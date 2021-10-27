package com.simple.mcghealth.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.entities.Bmi;
import com.simple.mcghealth.entities.User;
import com.simple.mcghealth.utils.CommonUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddInfoMainActivity extends AppCompatActivity {

    private EditText editTextFullName;
    private TextView textBirthDay;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private Spinner spinnerGender;
    private Button btnContinnue;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_main);

        initUtil();

        textBirthDay.setOnClickListener(view -> {
            initDatePicker(textBirthDay);
        });

        String[] genderList = {"Nam", "Nữ"};
        ArrayAdapter<String> adapterSpn = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, genderList);
        spinnerGender.setAdapter(adapterSpn);

        btnContinnue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void initUtil() {
        editTextFullName = findViewById(R.id.txtName);
        textBirthDay = findViewById(R.id.txtBirthday);
        editTextHeight = findViewById(R.id.txtHeight);
        editTextWeight = findViewById(R.id.txtWeight);
        spinnerGender = findViewById(R.id.spnGender);
        btnContinnue = findViewById(R.id.btnContinue);
    }

    private void addUser() {
        String username = editTextFullName.getText().toString().trim();
        String birthday = textBirthDay.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();

        String height = editTextHeight.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();


        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(birthday) || TextUtils.isEmpty(height) || TextUtils.isEmpty(weight)) {
            editTextFullName.setError("Không được trống");
            textBirthDay.setError("Không được trống");
            editTextWeight.setError("Không được trống");
            editTextHeight.setError("Không được trống");
            return;
        }
        Log.e("Info", "H: " + Float.parseFloat(height) + ", W: " + Float.parseFloat(weight));
        if (!checkDateInThePast(birthday)){
            Toast.makeText(this, "Ngày sinh không được sau ngày hiện tại", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setFullName(username);
        user.setBirthDay(birthday);
        user.setGender(gender);
        user.setDiseaseInfo("");
        user.setDiseaseInfoHistory("");

        try {
            appDatabase = AppDatabase.getInstance(getApplicationContext());
            List<User> i = appDatabase.userDao().getAllUser(); // lấy số lượng cũ
            appDatabase.userDao().insertUser(user);
            List<User> j = appDatabase.userDao().getAllUser(); // lấy số lượng mới - sau khi thêm
            if (j.size() > i.size()) {


                //Thêm thông tin về cân nặng chiều cao vào bảng BMI
                //Lấy id lớn nhất vừa thêm ở trên
                User u = appDatabase.userDao().getUserLast();
                Bmi bmi = new Bmi();
                bmi.setIdUser(u.getId());
                bmi.setDate(CommonUtils.getKeyToday());
                bmi.setHeight(Float.parseFloat(height));
                bmi.setWeight(Float.parseFloat(weight));

                try {
                    appDatabase.bmiDao().insertBMI(bmi);
                }catch (Exception ex){
                    Toast.makeText(this, "Lỗi xảy ra khi thêm bmi" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ERROR - INSERT BMI", ex.getMessage() + "\n" + ex.toString());
                }

                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddInfoMainActivity.this, MainActivity.class);
                startActivity(intent);
            }

        } catch (Exception x) {
            Toast.makeText(this, "Lỗi xảy ra khi thêm user" + x.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("ERROR - INSERT USER", x.getMessage() + "\n" + x.toString());
        }
    }

    private void initDatePicker(TextView textView) {
        DatePickerDialog datePickerDialog;
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            String date = day + "-" + month + "-" + year;
            textView.setText(date);
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    //Kiểm tra ngày sinh có trước sau thời điểm hiện tại không
    private boolean checkDateInThePast(String birthday) {
        Date b = CommonUtils.convertStringToDate(birthday);
        Date now = CommonUtils.convertStringToDate(CommonUtils.getKeyToday());

        if (b.after(now)) {
            return false;
        } else {
            return true;
        }
    }

}