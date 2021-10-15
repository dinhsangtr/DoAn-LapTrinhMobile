package com.simple.mcghealth.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.simple.mcghealth.R;
import com.simple.mcghealth.adapters.ThongTinDDAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThongTinDDActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    List<String> ListDataHeader;
    HashMap<String, List<String>> ListDataChild;
    ThongTinDDAdapter ThongTinDDAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ddactivity);
        addControl();
        ThongTinDDAdapter = new ThongTinDDAdapter(ThongTinDDActivity.this, ListDataHeader, ListDataChild);
        expandableListView.setAdapter(ThongTinDDAdapter);
    }


    private void addControl() {
        expandableListView = (ExpandableListView) findViewById(R.id.explv);
        ListDataHeader = new ArrayList<>();
        ListDataChild = new HashMap<String, List<String>>();

        ListDataHeader.add("Tháp dinh dưỡng");
        ListDataHeader.add("Thông tin dinh dưỡng");
        ListDataHeader.add("Phương pháp cân đối");

        List<String> thapdinhduong = new ArrayList<>();
        thapdinhduong.add("**********");
        thapdinhduong.add("**********");
        thapdinhduong.add("**********");

        List<String> thongtindinhduong = new ArrayList<>();


        thongtindinhduong.add("Protein");
        thongtindinhduong.add("Carbohydrate");
        thongtindinhduong.add("Chất béo");
        thongtindinhduong.add(" Vitamin");
        thongtindinhduong.add("Khoáng chất");
        thongtindinhduong.add("Nước");

        List<String> phuongphapdinhduong = new ArrayList<>();

        phuongphapdinhduong.add("Phương pháp tăng cân");
        phuongphapdinhduong.add("Phương pháp giảm cân");
        phuongphapdinhduong.add("Phương pháp cân đối");

        ListDataChild.put(ListDataHeader.get(0), thapdinhduong);
        ListDataChild.put(ListDataHeader.get(1), thongtindinhduong);
        ListDataChild.put(ListDataHeader.get(2), phuongphapdinhduong);

    }
}