package com.simple.mcghealth.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.simple.mcghealth.R;
import com.simple.mcghealth.utils.MyPreferences;

public class IntroFTActivity extends AppCompatActivity {
    Button btnOK;
    TextView txtPolicy;

    private MyPreferences myPreferences;


    @Override
    protected void onStart() {
        super.onStart();
        myPreferences = new MyPreferences(this);

        if (!myPreferences.isFirstTimeLaunch()) {
            startActivity(new Intent(this, IntroActivity.class));
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_ftactivity);

        btnOK = findViewById(R.id.btnOK);
        txtPolicy = findViewById(R.id.txtPolicy);

        btnOK.setOnClickListener(view -> {
            myPreferences.setFirstTimeLaunch(false);
            startActivity(new Intent(this, AboutActivity.class));
        });

        txtPolicy.setOnClickListener(view -> {
            //myPreferences.setFirstTimeLaunch(false);
        });
    }
}