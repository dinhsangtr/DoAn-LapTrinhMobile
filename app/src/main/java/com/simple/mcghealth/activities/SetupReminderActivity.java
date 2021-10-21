package com.simple.mcghealth.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.simple.mcghealth.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class SetupReminderActivity extends AppCompatActivity {
    private String SESSION1 = "m", SESSION2 = "n", SESSION3 = "e"; //morning, noon, evening

    private EditText edt1, edt2, edt3, edtNote;
    private Switch swt1, swt2, swt3;
    private TextView txtTime1, txtTime2, txtTime3, txtDateFrom, txtDateTo, txtDrugName;
    private ImageView imgDrug;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_reminder);
        mappings(); //ánh xạ
        actionToolbar(); //toolbar
        setSelectAllOnFocus();// bôi đen text khi click vào edittext
        //get Intent : medicineID
        intent = getIntent();
        int medicineID = intent.getIntExtra("medicineID", -1);
        if (medicineID == -1) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            db = AppDatabase.getInstance(getApplicationContext());
            List<MedicationTime> medicationTimeList = db.medicationTimeDao().getAllTime(medicineID); //Laays danh sach theo id medicine
            Medicine medicine = db.medicineDao().findMedicine(medicineID);

            //Gán thông tin của thuốc
            edtNote.setText(medicine.getNote());
            txtDrugName.setText(medicine.getName());

            Bitmap bitmap = CommonUtils.convertByteArrayToBitmap(medicine.getImage());
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
                setDataSource(0, 0, 0, "06:30", "12:00", "19:30", 0, 0, 0);
                txtDateFrom.setText(CommonUtils.getKeyToday());
                txtDateTo.setText(CommonUtils.getKeyToday());
            }

            btnSave.setOnClickListener(view -> {
                if (checkNullEdittextSL() == 0) {//thông báo lỗi khi ô bị trống
                    getDataUI(); //lay du lieu tu man hinh
                    List<MedicationTime> listForInsert = new ArrayList<>();
                    listForInsert.add(new MedicationTime(medicineID, SESSION1, sl1, time1, status1));
                    listForInsert.add(new MedicationTime(medicineID, SESSION2, sl2, time2, status2));
                    listForInsert.add(new MedicationTime(medicineID, SESSION3, sl3, time3, status3));

                    try {
                        db.medicationTimeDao().deleteTime(medicineID);
                        db.medicationTimeDao().insertMultiTime(listForInsert);
                        db.medicineDao().updateMedicine(medicineID, note, dateFrom, dateTo);
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "ERROR " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    CommonUtils.displayAlertDialog(this, "Đã được lưu", null);
                }
            });

            btnExit.setOnClickListener(view -> finish());
        }
    }


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

    }

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

    private void setSelectAllOnFocus() {
        edt1.setSelectAllOnFocus(true);
        edt2.setSelectAllOnFocus(true);
        edt3.setSelectAllOnFocus(true);
    }

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

}