package com.simple.mcghealth.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import com.simple.mcghealth.R;
import com.simple.mcghealth.interfaces.UpdateUiCallBack;
import com.simple.mcghealth.services.StepService;
import com.simple.mcghealth.utils.CommonUtils;


public class WalkingStepActivity extends AppCompatActivity {
    private boolean mIsBind;
    TextView txtStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //InitData
        showStepCount(CommonUtils.getStepNumber(), 0);

        //SetupService
        Intent intent = new Intent(this, StepService.class);
        mIsBind = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        startService(intent);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBind) {
            unbindService(mServiceConnection);
        }
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            showStepCount(CommonUtils.getStepNumber(), stepService.getStepCount());
            stepService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    showStepCount(CommonUtils.getStepNumber(), stepCount);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    public void showStepCount(int totalStepNum, int currentCounts) {
        if (currentCounts < totalStepNum) {
            currentCounts = totalStepNum;
        }
        txtStep = findViewById(R.id.text_step);
        txtStep.setText(String.valueOf(currentCounts));
    }


}