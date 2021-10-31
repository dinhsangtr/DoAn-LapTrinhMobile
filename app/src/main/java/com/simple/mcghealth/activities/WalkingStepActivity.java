package com.simple.mcghealth.activities;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.databinding.ActivityWalkingStepBinding;
import com.simple.mcghealth.entities.WalkingStep;
import com.simple.mcghealth.interfaces.UpdateUiCallBack;
import com.simple.mcghealth.services.StepService;
import com.simple.mcghealth.utils.CommonUtils;

import java.util.List;

public class WalkingStepActivity extends AppCompatActivity {
    private TextView txtStepTarget, txtStepCurrent;
    private ImageView imgViewEditTarget;
    private Toolbar toolbar;
    private String target = "";

    private AppDatabase db;

    //step
    private boolean mIsBind;
    private ActivityWalkingStepBinding mBinding;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            db = AppDatabase.getInstance(getApplicationContext());
            WalkingStep ws = db.walkingStepDao().getStep();
            showStepCount(ws.getStep(), 0);

            StepService stepService = ((StepService.StepBinder) service).getService();
            showStepCount(ws == null ? 0 : ws.getStep(), stepService.getStepCount());

            stepService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    showStepCount(ws == null ? 0 : ws.getStep(), stepCount);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_walking_step);
        initData();

        //ánh xạ
        txtStepCurrent = (TextView) findViewById(R.id.txtStepCurrent);
        txtStepTarget = (TextView) findViewById(R.id.txtStepTarget);
        imgViewEditTarget = (ImageView) findViewById(R.id.imgViewEditTarget);
        toolbar = (Toolbar) findViewById(R.id.toolbarStep);
        TextView muctieu = findViewById(R.id.muctieu);

        //
        ActionToolbar(toolbar);

        //Chỉnh sửa mục tiêu
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
                Toast.makeText(this, txtToast, Toast.LENGTH_LONG).show();
            });
        });

        //
        db = AppDatabase.getInstance(getApplicationContext());
        int sl = db.walkingStepDao().getAll().size();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBind) {
            unbindService(mServiceConnection);
        }
    }

    private void initData() {
        db = AppDatabase.getInstance(getApplicationContext());
        List<WalkingStep> wsList = db.walkingStepDao().getAll();
        if (wsList.size() == 0) {
            db.walkingStepDao().insert(new WalkingStep(0, 0, CommonUtils.getKeyToday()));
            WalkingStep ws = db.walkingStepDao().getStep();
            showStepCount(ws.getStep(), 0);
        } else {
            WalkingStep ws = db.walkingStepDao().getStep();
            showStepCount(ws.getStep(), 0);
        }
        setupService();
    }

    //gán lại khi số bước thay đổi
    public void showStepCount(int totalStepNum, int currentCounts) {
        if (currentCounts < totalStepNum) {
            currentCounts = totalStepNum;
            //cap nhat lai db
            db = AppDatabase.getInstance(getApplicationContext());
            WalkingStep ws = db.walkingStepDao().getStep();
            db.walkingStepDao().updateStep(ws.getId(), currentCounts);
        }
        mBinding.txtStepCurrent.setText(String.valueOf(currentCounts));
    }


    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        mIsBind = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }


    private void ActionToolbar(Toolbar toolbar) {
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