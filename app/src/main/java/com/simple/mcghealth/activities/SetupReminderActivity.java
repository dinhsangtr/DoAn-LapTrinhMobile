package com.simple.mcghealth.activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.entities.MedicationTime;
import com.simple.mcghealth.entities.Medicine;
import com.simple.mcghealth.entities.relations.MedicationWithTime;
import com.simple.mcghealth.receiver.AlarmReceiver;
import com.simple.mcghealth.utils.CommonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SetupReminderActivity extends AppCompatActivity {
    private String SESSION1 = "m", SESSION2 = "n", SESSION3 = "e"; //morning, noon, evening
    private int REQUEST_CODE_CAMERA = 1;
    private int REQUEST_CODE_FOLDER = 2;

    private EditText edt1, edt2, edt3, edtNote;
    private Switch swt1, swt2, swt3;
    private TextView txtTime1, txtTime2, txtTime3, txtDateFrom, txtDateTo, txtDrugName;
    private ImageView imgDrug;
    private LinearLayout layoutEdit;


    private Intent intent;
    private AppDatabase db;
    private List<MedicationTime> medicationTimes;
    private List<MedicationWithTime> medicationWithTimeList;

    private int sl1, sl2, sl3;
    private String time1, time2, time3;
    private String dateFrom, dateTo;
    private int status1, status2, status3; //status
    private String note;

    private Toolbar toolbar;
    private Button btnExit, btnSave;

    //Time Picker
    private int h, m;//hour, minute
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    private int medicineID;

    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_reminder);

        mappings(); //??nh x??? view
        actionToolbar(); //X??? l?? toolbar
        setSelectAllOnFocus();// B??i ??en text khi click v??o edittext --> ko c???n ph???i b???m x??a r???i nh???p s???


        //Get Intent :l???y medicineID : m?? thu???c
        intent = getIntent();
        medicineID = intent.getIntExtra("medicineID", -1); //l???y t??? adapter
        if (medicineID == -1) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            db = AppDatabase.getInstance(getApplicationContext());
            List<MedicationTime> medicationTimeList = db.medicationTimeDao().getAllTime(medicineID); //L???y danh s??ch th???i gian theo id medicine
            Medicine medicine = db.medicineDao().findMedicine(medicineID);

            //G??n th??ng tin c???a thu???c
            edtNote.setText(medicine.getNote());
            txtDrugName.setText(medicine.getName());

            Bitmap bitmap = CommonUtils.convertByteArrayToBitmap(medicine.getImage()); //image l??u d?????i d???ng byte[] --> chuy???n sang bitmap
            imgDrug.setImageBitmap(bitmap);

            //
            if (medicationTimeList.size() > 0) {
                for (int i = 0; i < medicationTimeList.size(); i++) {
                    if (medicationTimeList.get(i).getSession().equals("m")) {
                        sl1 = medicationTimeList.get(i).getCount();
                        time1 = medicationTimeList.get(i).getTimeDrink();
                        status1 = medicationTimeList.get(i).getStatus();
                    } else if (medicationTimeList.get(i).getSession().equals("n")) {
                        sl2 = medicationTimeList.get(i).getCount();
                        time2 = medicationTimeList.get(i).getTimeDrink();
                        status2 = medicationTimeList.get(i).getStatus();
                    } else if (medicationTimeList.get(i).getSession().equals("e")) {
                        sl3 = medicationTimeList.get(i).getCount();
                        time3 = medicationTimeList.get(i).getTimeDrink();
                        status3 = medicationTimeList.get(i).getStatus();
                    }
                }
                setDataSource(sl1, sl2, sl3, time1, time2, time3, status1, status2, status3);
                txtDateFrom.setText(medicine.getFromDate());
                txtDateTo.setText(medicine.getToDate());
            } else { // l???n ?????u m??? s??? ????? m???c ?????nh
                setDataSource(1, 1, 1, "06:30", "12:00", "19:30", 0, 0, 0);
                txtDateFrom.setText(CommonUtils.getKeyToday());
                txtDateTo.setText(CommonUtils.getKeyToday());
            }

            setOnClickDateAndTime();

            btnSave.setOnClickListener(view -> {

                if (checkNullEdittextSL() == 0) {//th??ng b??o l???i khi ?? b??? tr???ng
                    getDataUI(); //lay du lieu tu man hinh
                    if (!checkDate(dateFrom, dateTo)) {
                        CommonUtils.displayAlertDialog(this, "L???i", "Ng??y b???t ?????u kh??ng ???????c l???n h??n ng??y k???t th??c.");
                        return;
                    }

                    if (!checkDateInThePast(dateFrom)) {
                        CommonUtils.displayAlertDialog(this, "L???i", "Ng??y b???t ?????u kh??ng ???????c tr?????c ng??y h??m nay.");
                        return;
                    }

                    //H???y service c??
                    try {
                        stopServiceWhenUpdate();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "STOP SERVICE FAIL", Toast.LENGTH_LONG).show();
                        Log.e("SETUP - STOP SERVICE", ex.toString());
                    }


                    try {
                        //Ki???m tra xem c?? th??m v??o tr?????c ???? ch??a
                        List<MedicationTime> listMT = db.medicationTimeDao().getAllTime(medicineID);
                        if (listMT.size() == 0) {
                            List<MedicationTime> listForInsert = new ArrayList<>();
                            listForInsert.add(new MedicationTime(medicineID, SESSION1, sl1, time1, status1));
                            listForInsert.add(new MedicationTime(medicineID, SESSION2, sl2, time2, status2));
                            listForInsert.add(new MedicationTime(medicineID, SESSION3, sl3, time3, status3));
                            db.medicationTimeDao().insertMultiTime(listForInsert);
                        } else {
                            //db.medicationTimeDao().deleteTime(medicineID);
                            db.medicationTimeDao().updateTime(listMT.get(0).getId(), SESSION1, sl1, time1, status1);
                            db.medicationTimeDao().updateTime(listMT.get(1).getId(), SESSION2, sl2, time2, status2);
                            db.medicationTimeDao().updateTime(listMT.get(2).getId(), SESSION3, sl3, time3, status3);
                        }
                        db.medicineDao().updateMedicine(medicineID, note, dateFrom, dateTo); //update date From-To and note
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "INSERT OR UPDATE FAIL" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    String test = "== ";
                    try {
                        List<MedicationTime> listMTForRemind = db.medicationTimeDao().getAllTimeOn(medicineID);
                        if (listMTForRemind.size() > 0) {
                            Date dFrom = CommonUtils.convertStringToDate(dateFrom);
                            long dayStart = CommonUtils.fromDate(dFrom);
                            long distance2Day = CommonUtils.getDifferenceBetweenTwoDates(dateFrom, dateTo);  //l???y gi?? tr??? ????? ch???y v??ng l???p
                            int x = 0;

                            do {
                                Date dateAlarm = CommonUtils.toDate(dayStart);
                                for (int i = 0; i < listMTForRemind.size(); i++) {
                                    int REQUEST_CODE = Integer.parseInt(dateAlarm.toString().replace("-", "") + "" + listMTForRemind.get(i).getId());
                                    intent = new Intent(SetupReminderActivity.this, AlarmReceiver.class);
                                    intent.putExtra("REQUEST_CODE", REQUEST_CODE);
                                    intent.setAction("init_alarm");
                                    pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                                    String timeToDrink = listMTForRemind.get(i).getTimeDrink();
                                    int h = Integer.parseInt(timeToDrink != null ? timeToDrink.substring(0, 2) : "6");
                                    int m = Integer.parseInt(timeToDrink != null ? timeToDrink.substring(3, 5) : "0");

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTimeInMillis(System.currentTimeMillis());
                                    calendar.setTime(dateAlarm);


                                    calendar.set(Calendar.HOUR_OF_DAY, h);
                                    calendar.set(Calendar.MINUTE, m);
                                    calendar.set(Calendar.SECOND, 0);
                                    calendar.set(Calendar.MILLISECOND, 0);
                                    Log.e("SETUP - INIT" ,  dateAlarm.toString() + ", i = " + i);

                                    //l???y ng??y ra so s??nh
                                    //??o???n code x??? l??: c??c th???i gian tr?????c th???i ??i???m hi???n t???i s??? ko setAlarm cho n??.
                                    @SuppressLint("SimpleDateFormat")
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    String dayAlarm = simpleDateFormat.format(calendar.getTime());

                                    if (dayAlarm.equals(CommonUtils.getKeyToday())) {
                                        if (h > Integer.parseInt(getHourNow())) {
                                            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                                            test += h + ":" + m + " ==";
                                        } else if (h == Integer.parseInt(getHourNow())) {
                                            if (m >= Integer.parseInt(getMinuteNow())) {
                                                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                                                test += h + ":" + m + " ==";
                                            }
                                        }
                                    } else {
                                        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                                        test += h + ":" + m + " ==";
                                    }
                                }
                                dayStart += 24 * 60 * 60 * 1000; //1nagyf
                                x++;
                            } while (x < distance2Day + 1);
                        }
                        CommonUtils.displayAlertDialog(this, "???? ???????c l??u", null);
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "ERROR " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            btnExit.setOnClickListener(view -> finish());

            layoutEdit.setOnClickListener(view -> {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_edit_medicine_info);

                //Mapping
                Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
                Button btnSave = (Button) dialog.findViewById(R.id.btnSave);
                EditText edtDrugName = (EditText) dialog.findViewById(R.id.edtDrugName);
                ImageView imageViewGallery = (ImageView) dialog.findViewById(R.id.imageViewGallery);
                ImageView imageViewCamera = (ImageView) dialog.findViewById(R.id.imageViewCamera);
                ImageView imgReview = (ImageView) dialog.findViewById(R.id.imgReview);

                edtDrugName.setText(medicine.getName());
                imgReview.setImageBitmap(bitmap);

                //ImageView
                imageViewCamera.setOnClickListener(view1 -> {
                    //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    Toast.makeText(getApplicationContext(), "UPDATE SOON", Toast.LENGTH_SHORT).show();
                });

                imageViewGallery.setOnClickListener(view1 -> {
                    //Intent intent = new Intent(Intent.ACTION_PICK);
                    //intent.setType("image/*");
                    //startActivityForResult(intent, REQUEST_CODE_FOLDER);
                    Toast.makeText(getApplicationContext(), "UPDATE SOON", Toast.LENGTH_SHORT).show();
                });

                //Button
                btnSave.setOnClickListener(view1 -> {
                    String drugName = edtDrugName.getText().toString().trim();

                    if (TextUtils.isEmpty(drugName)){
                        edtDrugName.setError("T??n thu???c kh??ng ????? tr???ng!");
                    }else{
                        //BitmapDrawable bitmapDrawable = (BitmapDrawable) imgReview.getDrawable();
                        //Bitmap bitmap1 = bitmapDrawable.getBitmap();
                        //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        //bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        //byte[] image = byteArrayOutputStream.toByteArray();
                        //image = CommonUtils.imagemTratada(image);

                        db = AppDatabase.getInstance(this.getApplicationContext());
                        db.medicineDao().updateMedicine(medicineID, drugName);

                        txtDrugName.setText(drugName);
                        CommonUtils.hideSoftKeyBoard(this);
                        CommonUtils.displayAlertDialog(this, "S???a th??nh c??ng", null);
                    }
                });

                btnExit.setOnClickListener(view1 -> {
                    dialog.cancel();
                    dialog.dismiss();
                });

                dialog.show();
            });
        }
    }


    //H???y c??c service c?? khi c???p nh???t l???i th???i gian m???i
    private void stopServiceWhenUpdate() throws ParseException {
        List<MedicationTime> listMT = db.medicationTimeDao().getAllTime(medicineID);
        if (listMT.size() > 0) {
            List<Integer> listReQuestCode = new ArrayList<>();
            Medicine medicine = db.medicineDao().findMedicine(medicineID);

            Date dayStart = CommonUtils.convertStringToDate(medicine.getFromDate());
            long day = CommonUtils.fromDate(dayStart);
            long distance = CommonUtils.getDifferenceBetweenTwoDates(medicine.getFromDate(), medicine.getToDate());

            List<MedicationTime> listMTForRemind = db.medicationTimeDao().getAllTimeOn(medicineID);


            for (int i = 0; i < distance + 1; i++) {
                Date dateAlarm = CommonUtils.toDate(day);
                for (int j = 0; j < listMTForRemind.size(); j++) {
                    listReQuestCode.add(Integer.parseInt(dateAlarm.toString().replace("-", "") + "" + listMTForRemind.get(i).getId()));
                }
                day += 24 * 60 * 60 * 1000;
            }

            //Stop service

            for (int i = 0; i < listReQuestCode.size(); i++) {
                intent = new Intent(SetupReminderActivity.this, AlarmReceiver.class);
                intent.setAction("stop_alarm");
                pendingIntent = PendingIntent.getBroadcast(this, listReQuestCode.get(i), intent, PendingIntent.FLAG_NO_CREATE);
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                if (pendingIntent != null) {
                    alarmManager.cancel(pendingIntent);
                    Log.e("SETUP - CANCEL", listReQuestCode.get(i).toString());
                }
            }
        }
    }

    //L???y d??? li???u tr??n m??n h??nh ????? l??u
    private void getDataUI() {
        //int - s??? l?????ng
        sl1 = Integer.parseInt(edt1.getText().toString().trim());
        sl2 = Integer.parseInt(edt2.getText().toString().trim());
        sl3 = Integer.parseInt(edt3.getText().toString().trim());

        //string - th???i gian u???ng
        time1 = txtTime1.getText().toString();
        time2 = txtTime2.getText().toString();
        time3 = txtTime3.getText().toString();

        //int - tr???ng th??i
        status1 = swt1.isChecked() ? 1 : 0;
        status2 = swt2.isChecked() ? 1 : 0;
        status3 = swt3.isChecked() ? 1 : 0;

        //string - ng??y u???ng
        dateFrom = txtDateFrom.getText().toString();
        dateTo = txtDateTo.getText().toString();

        //ghi ch??
        note = edtNote.getText().toString().trim();
    }

    //PICKER - ch???n th???i gian v?? ng??y th??ng
    private void initTimePicker(TextView textView) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (TimePickerDialog.OnTimeSetListener) (timePicker, hourOfDay, minute) -> {
                    h = hourOfDay;
                    m = minute;
                    String time = h + ":" + m;
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat h24Format = new SimpleDateFormat("HH:mm");
                    try {
                        Date date = h24Format.parse(time);
                        textView.setText(h24Format.format(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "ERROR " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, 24, 0, false);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(h, m);
        timePickerDialog.show();
    }

    private void initDatePicker(TextView textView) {
        DatePickerDialog datePickerDialog;
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "-" + month + "-" + year;
                textView.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private void setOnClickDateAndTime() {
        txtTime1.setOnClickListener(view -> {
            initTimePicker(txtTime1);
        });
        txtTime2.setOnClickListener(view -> {
            initTimePicker(txtTime2);
        });
        txtTime3.setOnClickListener(view -> {
            initTimePicker(txtTime3);
        });

        txtDateFrom.setOnClickListener(view -> {
            initDatePicker(txtDateFrom);
        });
        txtDateTo.setOnClickListener(view -> {
            initDatePicker(txtDateTo);
        });
    }

    private static String getHourNow() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        return simpleDateFormat.format(calendar.getTime());
    }

    private static String getMinuteNow() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm");
        return simpleDateFormat.format(calendar.getTime());
    }

    //Ki???m tra ng??y b???t ?????u c?? sau ng??y k???t th??c kh??ng?
    private boolean checkDate(String dateFrom, String dateTo) {
        try {

            @SuppressLint("SimpleDateFormat") Date dateF = new SimpleDateFormat("dd-MM-yyyy").parse(dateFrom);
            @SuppressLint("SimpleDateFormat") Date dateT = new SimpleDateFormat("dd-MM-yyyy").parse(dateTo);

            if (dateF.after(dateT)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Ki???m tra ng??y b???t ?????u c?? tr?????c th??i ??i???m hi???n t???i kh??ng
    private boolean checkDateInThePast(String dateFrom) {
        Date dateF = CommonUtils.convertStringToDate(dateFrom);
        Date now = CommonUtils.convertStringToDate(CommonUtils.getKeyToday());

        if (now.after(dateF)) {
            return false;
        } else {
            return true;
        }
    }

    //sl l?? s??? l?????ng
    //t l?? time
    //swt l?? trang th??i switch
    @SuppressLint("SetTextI18n")
    private void setDataSource(int sl1, int sl2, int sl3, String t1, String t2, String t3, int sw1, int sw2, int sw3) {
        edt1.setText(String.valueOf(sl1));
        edt2.setText(String.valueOf(sl2));
        edt3.setText(String.valueOf(sl3));

        txtTime1.setText(t1);
        txtTime2.setText(t2);
        txtTime3.setText(t3);

        swt1.setChecked(sw1 != 0);
        swt2.setChecked(sw2 != 0);
        swt3.setChecked(sw3 != 0);
    }

    // B??i ??en text khi click v??o edittext --> ko c???n ph???i b???m x??a r???i nh???p s???
    private void setSelectAllOnFocus() {
        edt1.setSelectAllOnFocus(true);
        edt2.setSelectAllOnFocus(true);
        edt3.setSelectAllOnFocus(true);
    }

    //Check null edittext nh???p s??? l?????ng
    private int checkNullEdittextSL() {
        int s = 0;
        if (TextUtils.isEmpty(edt1.getText().toString())) {
            edt1.setError("Vui l??ng nh???p s??? l?????ng");
            s++;
        }
        if (TextUtils.isEmpty(edt1.getText().toString())) {
            edt1.setError("Vui l??ng nh???p s??? l?????ng");
            s++;
        }
        if (TextUtils.isEmpty(edt1.getText().toString())) {
            edt1.setError("Vui l??ng nh???p s??? l?????ng");
            s++;
        }
        return s;
    }

    //??nh x??? view
    private void mappings() {
        edt1 = findViewById(R.id.edtSLBS);
        edt2 = findViewById(R.id.edtSLBTR);
        edt3 = findViewById(R.id.edtSLBT);

        swt1 = findViewById(R.id.switchOnOffBS);
        swt2 = findViewById(R.id.switchOnOffBTR);
        swt3 = findViewById(R.id.switchOnOffBT);

        txtTime1 = findViewById(R.id.setTimeBS);
        txtTime2 = findViewById(R.id.setTimeBTR);
        txtTime3 = findViewById(R.id.setTimeBT);

        txtDateFrom = findViewById(R.id.fromTime);
        txtDateTo = findViewById(R.id.toTime);

        edtNote = findViewById(R.id.edtNote);
        txtDrugName = findViewById(R.id.txtDrugName);
        imgDrug = findViewById(R.id.imageDrug);

        toolbar = findViewById(R.id.toolbarRemind);
        btnExit = findViewById(R.id.btnExit);
        btnSave = findViewById(R.id.btnSave);

        layoutEdit = findViewById(R.id.lnInfo);
    }

    //Quay v??? c???a toolbar
    private void actionToolbar() {
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}