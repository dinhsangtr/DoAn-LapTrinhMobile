package com.simple.mcghealth.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.simple.mcghealth.R;
import com.simple.mcghealth.utils.CommonUtils;

public class About1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about1);
        //Nút quay trở lại trang trước trên thanh toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        CommonUtils.actionToolbar(toolbar, this);
    }
}