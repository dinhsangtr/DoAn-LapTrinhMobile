package com.simple.mcghealth.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.simple.mcghealth.R;


public class WalkingStepActivity extends AppCompatActivity {
    private TextView txtStepTarget, txtStepCurrent;
    private ImageView imgViewEditTarget;
    private Toolbar toolbar;

    private String target = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_step);

        txtStepCurrent = (TextView) findViewById(R.id.txtStepCurrent);
        txtStepTarget = (TextView) findViewById(R.id.txtStepTarget);
        imgViewEditTarget = (ImageView) findViewById(R.id.imgViewEditTarget);
        toolbar = (Toolbar) findViewById(R.id.toolbarStep);

        ActionToolbar();
        target = txtStepTarget.getText().toString();


        imgViewEditTarget.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_update_target);
            int width = WindowManager.LayoutParams.MATCH_PARENT;
            int height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();

            EditText editText = dialog.findViewById(R.id.edtTarget);
            Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

            editText.setText(target);

            btnUpdate.setOnClickListener(view1 -> {
                String str = editText.getText().toString().trim();

                ///////sql chưa có
                dialog.dismiss();
                String txtToast = "Update Successfully!";
                Toast.makeText(this, txtToast , Toast.LENGTH_LONG).show();
            });
        });

    }

    private void ActionToolbar() {
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}