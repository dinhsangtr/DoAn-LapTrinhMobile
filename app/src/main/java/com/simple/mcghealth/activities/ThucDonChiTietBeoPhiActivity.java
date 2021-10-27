package com.simple.mcghealth.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.simple.mcghealth.R;

public class ThucDonChiTietBeoPhiActivity extends AppCompatActivity {
    Button btnThucDon;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_thuc_don_chi_tiet_beo_phi);
        this.btnThucDon = (Button) findViewById(R.id.buttongoiythucdon);
        this.btnThucDon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ThucDonChiTietBeoPhiActivity.this, com.simple.mcghealth.activities.ListBaiVietActivity.class);
                intent.putExtra("so", 4);
                ThucDonChiTietBeoPhiActivity.this.startActivity(intent);
            }
        });
    }
}

