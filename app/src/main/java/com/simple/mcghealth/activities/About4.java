package com.simple.mcghealth.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.simple.mcghealth.R;
import com.simple.mcghealth.utils.CommonUtils;

public class About4 extends AppCompatActivity {

    private Toolbar toolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about4);
        toolbar = findViewById(R.id.toolbar);
        CommonUtils.actionToolbar(toolbar, this);

    }
}