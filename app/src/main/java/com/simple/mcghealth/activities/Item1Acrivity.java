package com.simple.mcghealth.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.simple.mcghealth.R;
import com.simple.mcghealth.entities.practice;
import com.simple.mcghealth.utils.CommonUtils;

import java.util.List;

public class Item1Acrivity extends AppCompatActivity {

    TextView title;
    TextView content;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item1_acrivity);
        raDiate();

        CommonUtils.actionToolbar(toolbar, this);
        List<practice> practiceList = practice.lisTTD();
        Intent intent = getIntent();
        try {
            int Id_pratice = intent.getIntExtra("Id_pratice",-1);

            if (Id_pratice != -1){
                //practice p = new practice();
                for (int i = 0; i < practiceList.size(); i++){
                    if (practiceList.get(i).getId()==Id_pratice){

                        title.setText(practiceList.get(i).getName());
                        content.setText(practiceList.get(i).getContent());
                    }

                }

            }
            else {
                title.setText("null ");
                content.setText("null ");
            }
        }
        catch (Exception x){
            Toast.makeText(this, "Lỗi dữ liệu" + x.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }
    private void raDiate(){
        title= findViewById(R.id.title);
        content=findViewById(R.id.content);
        toolbar=findViewById(R.id.toolbarAddDrug1);

    }
}