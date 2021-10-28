package com.simple.mcghealth.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.simple.mcghealth.R;
import com.simple.mcghealth.utils.CommonUtils;

public class UserManualActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manual);
        toolbar = findViewById(R.id.toolbarAddDrug1);
        CommonUtils.actionToolbar(toolbar, this);
    }
}