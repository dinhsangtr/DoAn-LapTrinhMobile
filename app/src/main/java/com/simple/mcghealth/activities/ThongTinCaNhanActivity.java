package com.simple.mcghealth.activities;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.entities.Bmi;
import com.simple.mcghealth.entities.User;
import com.simple.mcghealth.utils.CommonUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.jar.Attributes;

public class ThongTinCaNhanActivity extends AppCompatActivity {
    private AppDatabase db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.thongtinncanhan_activity);

        TextView txtTuoi = (TextView) findViewById(R.id.txtBirthday);
        TextView txtChieuCao = (TextView) findViewById(R.id.txtHeight);
        TextView txtCanNang = (TextView) findViewById(R.id.txtWeight);
        TextView txtTTBenh = (TextView) findViewById(R.id.txtThongtinbenh);
        TextView txtBMI = (TextView) findViewById(R.id.txtBMI);
        TextView txtTienSuBenh = (TextView) findViewById(R.id.txtTieuSu);
        TextView txtTenNguoiDung = (TextView) findViewById(R.id.txtTenNguoiDung);
        ImageView btnChinhSua = (ImageView) findViewById(R.id.btnSua1);
        ImageView btnChinhSua2 = (ImageView) findViewById(R.id.btnSua2);


        Intent intent = getIntent();
        int IdUser = intent.getIntExtra("IdUser", -1);
        if (IdUser != -1) {

            db = AppDatabase.getInstance(getApplicationContext());
            User user = db.userDao().getUserById(IdUser);
            if (user != null) {
                //laythongtinvadoramanhinh
                txtTenNguoiDung.setText(user.getFullName());

                //laytuoi
                long age = 0;
                try {
                    long distanceYear = CommonUtils.getDifferenceBetweenTwoDates(user.getBirthDay(), CommonUtils.getKeyToday());
                    age = Math.round(distanceYear / 365);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                txtTuoi.setText(age + " tuổi");

                //lay bmi
                Bmi bmi = db.bmiDao().getBMILast(user.getId());

                //Chieucao
                txtChieuCao.setText(txtChieuCao.getText().toString() + " " + bmi.getHeight());
                //Cannang
                txtCanNang.setText(txtCanNang.getText().toString() + " " + bmi.getWeight());


                float i = (bmi.getHeight() * 2 / 100);
                int ChiSoBmi = Math.round(bmi.getWeight() / i);

                txtBMI.setText(txtBMI.getText().toString() + " " + ChiSoBmi);

                String ThongTinBenh = user.getDiseaseInfo();
                String TienSubenh = user.getDiseaseInfoHistory();

                if (!ThongTinBenh.equals("")) {
                    txtTTBenh.setText(txtTTBenh.getText().toString() + " " + ThongTinBenh);
                } else {
                    txtTTBenh.setText(txtTTBenh.getText().toString() + " Không có");
                }

                if (!TienSubenh.equals("")) {
                    txtTienSuBenh.setText(txtTienSuBenh.getText().toString() + " " + TienSubenh);
                } else {
                    txtTienSuBenh.setText(txtTienSuBenh.getText().toString() + " Không có");

                }

                // XU LY DIALOG CHINH SUA THÔNG TIN
                btnChinhSua.setOnClickListener(view -> {
                    Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.dialog_chinhsuathongtincoban);

                    //Mapping
                    Button btnLuu = (Button) dialog.findViewById(R.id.btnLuu);
                    Button btnThoat = (Button) dialog.findViewById(R.id.btnThoat);
                    EditText edtTenNguoiDung = (EditText) dialog.findViewById(R.id.txtTenNguoiDung);
                    TextView edtTuoi = (TextView) dialog.findViewById(R.id.txtAge);
                    EditText edtChieuCao = (EditText) dialog.findViewById(R.id.txtHeight);
                    EditText edtCanNang = (EditText) dialog.findViewById(R.id.txtWeight);

                    edtTenNguoiDung.setText(user.getFullName());
                    edtTuoi.setText(user.getBirthDay());
                    edtChieuCao.setText(bmi.getHeight() + "");
                    edtCanNang.setText(bmi.getWeight() + "");


                    txtTuoi.setOnClickListener(view1 -> {
                        initDatePicker(txtTuoi);
                    });
                    // Button luu
                    btnLuu.setOnClickListener(view1 -> {
                        String name = edtTenNguoiDung.getText().toString().trim();
                        String birthday = edtTuoi.getText().toString().trim();
                        String chieucao = edtChieuCao.getText().toString().trim();
                        String cannang = edtCanNang.getText().toString().trim();

                        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(birthday) || TextUtils.isEmpty(chieucao) || TextUtils.isEmpty(cannang)) {
                            edtCanNang.setError("không để trống!");

                        } else {
                            //update ten va ngay sinh
                            db = AppDatabase.getInstance(this.getApplicationContext());
                            db.userDao().updateUser(IdUser, name, birthday);

                            //update bmi
                            Bmi bmi1 = new Bmi();
                            bmi1.setIdUser(IdUser);
                            bmi1.setDate(CommonUtils.getKeyToday());
                            bmi1.setHeight(Float.parseFloat(chieucao));
                            bmi1.setWeight(Float.parseFloat(cannang));

                            try {
                                db.bmiDao().insertBMI(bmi1);
                            } catch (Exception ex) {
                                Toast.makeText(this, "Lỗi xảy ra khi thêm bmi" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("ERROR - INSERT BMI", ex.getMessage() + "\n" + ex.toString());
                            }
                            CommonUtils.hideSoftKeyBoard(this);
                            CommonUtils.displayAlertDialog(this, "Sửa thành công", null);
                        }
                    });


                    btnThoat.setOnClickListener(view1 -> {
                        dialog.cancel();
                        dialog.dismiss();
                    });

                    dialog.show();


                });


                btnChinhSua2.setOnClickListener(view -> {
                    Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.dialog_chinhsuathongtinbenhan);

                    //Mapping
                    Button btnLuu = (Button) dialog.findViewById(R.id.btnLuu);
                    Button btnThoat = (Button) dialog.findViewById(R.id.btnThoat);
                    EditText edtThongTinBenh = (EditText) dialog.findViewById(R.id.edtTTBenh);
                    EditText edtTienSu = (EditText) dialog.findViewById(R.id.edtTienSuBenh);


                    edtThongTinBenh.setText(user.getDiseaseInfo());
                    edtTienSu.setText(user.getDiseaseInfoHistory());


                    // Button luu
                    btnLuu.setOnClickListener(view1 -> {
                        String ttb = edtThongTinBenh.getText().toString().trim();
                        String ts = edtTienSu.getText().toString().trim()  + "\n" + user.getDiseaseInfo() ;


                        //update ten va ngay sinh
                        db = AppDatabase.getInstance(this.getApplicationContext());
                        db.userDao().updateUserDisease(IdUser, ttb, ts);

                        CommonUtils.hideSoftKeyBoard(this);
                        CommonUtils.displayAlertDialog(this, "Sửa thành công", null);

                    });

                    btnThoat.setOnClickListener(view1 -> {
                        dialog.cancel();
                        dialog.dismiss();
                    });

                    dialog.show();


                });


            }


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
}



