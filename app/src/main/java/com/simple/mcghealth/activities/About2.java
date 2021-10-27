package com.simple.mcghealth.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.simple.mcghealth.R;
import com.simple.mcghealth.utils.CommonUtils;

public class About2 extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about2);
        toolbar = findViewById(R.id.toolbarAddDrug2);
        CommonUtils.actionToolbar(toolbar, this);
    }
}