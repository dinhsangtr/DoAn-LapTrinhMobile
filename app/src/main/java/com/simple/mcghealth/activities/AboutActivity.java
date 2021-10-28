package com.simple.mcghealth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.simple.mcghealth.R;

public class AboutActivity extends AppCompatActivity {

    //private Toolbar toolbar;
    private LinearLayout ln1,ln2,ln3;
    private Button btnuse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        raDiate();

        this.ln1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.this.startActivity(new Intent(AboutActivity.this,About2.class));
            }
        });
        this.ln2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.this.startActivity(new Intent(AboutActivity.this, About3.class));
            }
        });
        this.ln3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.this.startActivity(new Intent(AboutActivity.this, About4.class));
            }
        });
        this.btnuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.this.startActivity(new Intent(AboutActivity.this, AddInfoMainActivity.class));
            }
        });

    }
    private void raDiate(){
        ln1= findViewById(R.id.ln2);
        ln2=findViewById(R.id.ln4);
        ln3=findViewById(R.id.ln5);
        btnuse=findViewById(R.id.btnuse);
    }
}