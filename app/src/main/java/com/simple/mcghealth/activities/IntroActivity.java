package com.simple.mcghealth.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.simple.mcghealth.R;

public class IntroActivity extends AppCompatActivity {

    int counter;
    TextView textView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        textView = findViewById(R.id.txtProgress);
        Thread thread = new Thread(this::startProgress);
        thread.start();
    }

    @SuppressLint("SetTextI18n")
    private void startProgress() {
        for (counter = 0; counter <= 100; counter++) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            handler.post(() -> textView.setText(String.valueOf(counter) + "%"));

            if (counter == 100){
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }

    }
}