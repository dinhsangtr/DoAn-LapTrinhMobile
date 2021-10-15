package com.simple.mcghealth.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.simple.mcghealth.R;
import com.simple.mcghealth.utils.MyPreferences;

public class IntroFTActivity extends AppCompatActivity {
    Intent intent;
    Button btnOK;
    TextView txtPolicy;

    @Override
    protected void onStart() {
        super.onStart();
        boolean isFirst = MyPreferences.isFirst(IntroFTActivity.this);
        if (!isFirst) {
            intent = new Intent(IntroFTActivity.this, IntroActivity.class);
            startActivity(intent);
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
            intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        });

        txtPolicy.setOnClickListener(view -> {

        });

    }
}