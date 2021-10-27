package com.simple.mcghealth.activities.diet;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.simple.mcghealth.R;
import com.simple.mcghealth.utils.CommonUtils;

public class ThucDonActivity extends AppCompatActivity {
    TextView txtAnDu;
    TextView txtBeoPhi;
    TextView txtGiam;
    TextView txtTang;
    TextView txtTieuDuong;
    private Toolbar toolbar;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_thuc_don);
        anhXa();
        CommonUtils.actionToolbar(toolbar, this);

        this.txtTang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ThucDonActivity.this.startActivity(new Intent(ThucDonActivity.this, ThucDonChiTietActivity.class));
            }
        });
        this.txtGiam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ThucDonActivity thucDonActivity = ThucDonActivity.this;
                thucDonActivity.startActivity(new Intent(thucDonActivity, ThucDonChiTietGiamActivity.class));
            }
        });
        this.txtAnDu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ThucDonActivity thucDonActivity = ThucDonActivity.this;
                thucDonActivity.startActivity(new Intent(thucDonActivity, ThucDonChiTietNoActivity.class));
            }
        });
        this.txtBeoPhi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ThucDonActivity thucDonActivity = ThucDonActivity.this;
                thucDonActivity.startActivity(new Intent(thucDonActivity, ThucDonChiTietBeoPhiActivity.class));
            }
        });
        this.txtTieuDuong.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ThucDonActivity thucDonActivity = ThucDonActivity.this;
                thucDonActivity.startActivity(new Intent(thucDonActivity, ThucDonChiTietTieuDuongActivity.class));
            }
        });
    }

    private void anhXa() {
        this.txtAnDu = (TextView) findViewById(R.id.textviewchedoandu);
        this.txtGiam = (TextView) findViewById(R.id.textviewchedogiamcan);
        this.txtTang = (TextView) findViewById(R.id.textviewchedotangcan);
        this.txtBeoPhi = (TextView) findViewById(R.id.textviewchedobeophi);
        this.txtTieuDuong = (TextView) findViewById(R.id.textviewchedotieuduong);
        this.toolbar = (Toolbar) findViewById(R.id.toolbarCDAU);
    }
}

