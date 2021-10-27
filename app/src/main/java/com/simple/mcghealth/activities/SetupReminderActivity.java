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

        mappings(); //Ánh xạ view
        actionToolbar(); //Xử lý toolbar
        setSelectAllOnFocus();// Bôi đen text khi click vào edittext --> ko cần phải bấm xóa rồi nhập số


        //Get Intent :lấy medicineID : mã thuốc
        intent = getIntent();
        medicineID = intent.getIntExtra("medicineID", -1); //lấy từ adapter
        if (medicineID == -1) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            db = AppDatabase.getInstance(getApplicationContext());
            List<MedicationTime> medicationTimeList = db.medicationTimeDao().getAllTime(medicineID); //Lấy danh sách thời gian theo id medicine
            Medicine medicine = db.medicineDao().findMedicine(medicineID);

            //Gán thông tin của thuốc
            edtNote.setText(medicine.getNote());
            txtDrugName.setText(medicine.getName());

            Bitmap bitmap = CommonUtils.convertByteArrayToBitmap(medicine.getImage()); //image lưu dưới dạng byte[] --> chuyển sang bitmap
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
            } else { // lần đầu mở sẽ để mặc định
                setDataSource(1, 1, 1, "06:30", "12:00", "19:30", 0, 0, 0);
                txtDateFrom.setText(CommonUtils.getKeyToday());
                txtDateTo.setText(CommonUtils.getKeyToday());
            }

            setOnClickDateAndTime();

            btnSave.setOnClickListener(view -> {

                if (checkNullEdittextSL() == 0) {//thông báo lỗi khi ô bị trống
                    getDataUI(); //lay du lieu tu man hinh
                    if (!checkDate(dateFrom, dateTo)) {
                        CommonUtils.displayAlertDialog(this, "Lỗi", "Ngày bắt đầu không được lớn hơn ngày kết thúc.");
                        return;
                    }

                    if (!checkDateInThePast(dateFrom)) {
                        CommonUtils.displayAlertDialog(this, "Lỗi", "Ngày bắt đầu không được trước ngày hôm nay.");
                        return;
                    }

                    //Hủy service cũ
                    try {
                        stopServiceWhenUpdate();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "STOP SERVICE FAIL", Toast.LENGTH_LONG).show();
                        Log.e("SETUP - STOP SERVICE", ex.toString());
                    }


                    try {
                        //Kiểm tra xem có thêm vào trước đó chưa
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
                            long distance2Day = CommonUtils.getDifferenceBetweenTwoDates(dateFrom, dateTo);  //lấy giá trị để chạy vòng lập
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

                                    //lấy ngày ra so sánh
                                    //Đoạn code xử lý: các thời gian trước thời điểm hiện tại sẽ ko setAlarm cho nó.
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
                        CommonUtils.displayAlertDialog(this, "Đã được lưu", null);
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
                        edtDrugName.setError("Tên thuốc không để trống!");
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
                        CommonUtils.displayAlertDialog(this, "Sửa thành công", null);
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


    //Hủy các service cũ khi cập nhật lại thời gian mới
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

    //Lấy dữ liệu trên màn hình để lưu
    private void getDataUI() {
        //int - số lượng
        sl1 = Integer.parseInt(edt1.getText().toString().trim());
        sl2 = Integer.parseInt(edt2.getText().toString().trim());
        sl3 = Integer.parseInt(edt3.getText().toString().trim());

        //string - thời gian uống
        time1 = txtTime1.getText().toString();
        time2 = txtTime2.getText().toString();
        time3 = txtTime3.getText().toString();

        //int - trạng thái
        status1 = swt1.isChecked() ? 1 : 0;
        status2 = swt2.isChecked() ? 1 : 0;
        status3 = swt3.isChecked() ? 1 : 0;

        //string - ngày uống
        dateFrom = txtDateFrom.getText().toString();
        dateTo = txtDateTo.getText().toString();

        //ghi chú
        note = edtNote.getText().toString().trim();
    }

    //PICKER - chọn thời gian và ngày tháng
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

    //Kiểm tra ngày bắt đầu có sau ngày kết thúc không?
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

    //Kiểm tra ngày bắt đầu có trước thòi điểm hiện tại không
    private boolean checkDateInThePast(String dateFrom) {
        Date dateF = CommonUtils.convertStringToDate(dateFrom);
        Date now = CommonUtils.convertStringToDate(CommonUtils.getKeyToday());

        if (now.after(dateF)) {
            return false;
        } else {
            return true;
        }
    }

    //sl là số lượng
    //t là time
    //swt là trang thái switch
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

    // Bôi đen text khi click vào edittext --> ko cần phải bấm xóa rồi nhập số
    private void setSelectAllOnFocus() {
        edt1.setSelectAllOnFocus(true);
        edt2.setSelectAllOnFocus(true);
        edt3.setSelectAllOnFocus(true);
    }

    //Check null edittext nhập số lượng
    private int checkNullEdittextSL() {
        int s = 0;
        if (TextUtils.isEmpty(edt1.getText().toString())) {
            edt1.setError("Vui lòng nhập số lượng");
            s++;
        }
        if (TextUtils.isEmpty(edt1.getText().toString())) {
            edt1.setError("Vui lòng nhập số lượng");
            s++;
        }
        if (TextUtils.isEmpty(edt1.getText().toString())) {
            edt1.setError("Vui lòng nhập số lượng");
            s++;
        }
        return s;
    }

    //Ánh xạ view
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

    //Quay về của toolbar
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