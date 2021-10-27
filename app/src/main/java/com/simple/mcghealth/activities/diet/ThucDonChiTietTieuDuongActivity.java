package com.simple.mcghealth.activities.diet;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.simple.mcghealth.R;

public class ThucDonChiTietTieuDuongActivity extends AppCompatActivity {
    Button btnThucDon;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_thuc_don_chi_tiet_tieu_duong);
        this.btnThucDon = (Button) findViewById(R.id.buttonthamkhaothucdon);
        this.btnThucDon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ThucDonChiTietTieuDuongActivity.this, com.simple.mcghealth.activities.ListBaiVietActivity.class);
                intent.putExtra("so", 5);
                ThucDonChiTietTieuDuongActivity.this.startActivity(intent);
            }
        });
    }
}
