package com.simple.mcghealth.activities.diet;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.simple.mcghealth.R;

public class ThucDonChiTietNoActivity extends AppCompatActivity {
    Button btn;
    Button btnThucDon;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_thuc_don_chi_tiet_no);
        this.btn = (Button) findViewById(R.id.buttongoiythucdon);
        this.btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ThucDonChiTietNoActivity thucDonChiTietNoActivity = ThucDonChiTietNoActivity.this;
                thucDonChiTietNoActivity.startActivity(new Intent(thucDonChiTietNoActivity, ThucDon7NgayNoActivity.class));
            }
        });
        this.btnThucDon = (Button) findViewById(R.id.buttonthamkhaothucdon);
        this.btnThucDon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ThucDonChiTietNoActivity.this, com.simple.mcghealth.activities.ListBaiVietActivity.class);
                intent.putExtra("so", 3);
                ThucDonChiTietNoActivity.this.startActivity(intent);
            }
        });
    }
}
