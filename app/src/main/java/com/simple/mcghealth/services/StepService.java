package com.simple.mcghealth.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.NotificationChannel;
import com.simple.mcghealth.R;
import com.simple.mcghealth.activities.WalkingStepActivity;
import com.simple.mcghealth.entities.WalkingStep;
import com.simple.mcghealth.interfaces.UpdateUiCallBack;

public class StepService extends Service implements SensorEventListener {
    private static final String TAG = "TAG: " + StepService.class.getSimpleName();
    private static int mStepSensorType = -1;

    private UpdateUiCallBack mCallback;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    private BroadcastReceiver mBroadcastReceiver;
    private StepBinder mStepBinder = new StepBinder();
    private SensorManager mSensorManager;

    private int mCurrentStep;
    private int mNotifyIdStep = 100;
    private int mHasStepCount = 0;
    private int mPreviousStepCount = 0;
    private boolean mHasRecord;

    private AppDatabase db;

    public class StepBinder extends Binder {
        public StepService getService() {
            return StepService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initNotification();
        initTodayData();
        initBroadcastReceiver();
        new Thread(new Runnable() {
            public void run() {
                startStepDetector();
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStepBinder;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (mStepSensorType) {
            case Sensor.TYPE_STEP_COUNTER:
                int tempStep = (int) sensorEvent.values[0];
                Log.e(TAG, "tempStep = " + tempStep);
                if (!mHasRecord) {
                    mHasRecord = true;
                    mHasStepCount = tempStep;
                } else {
                    int thisStepCount = tempStep - mHasStepCount;
                    int thisStep = thisStepCount - mPreviousStepCount;
                    mCurrentStep += thisStep;
                    mPreviousStepCount = thisStepCount;
                }
                break;
            case Sensor.TYPE_STEP_DETECTOR:
                if (sensorEvent.values[0] == 1.0) {
                    mCurrentStep++;
                }
                break;
        }
        updateNotification();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void startStepDetector() {
        if (mSensorManager != null) {
            mSensorManager = null;
        }
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        int VERSION_CODES = Build.VERSION.SDK_INT;
        if (VERSION_CODES >= 19) {
            addCountStepListener();
        } else {
            addBasePedometerListener();
        }
    }

    public void registerCallback(UpdateUiCallBack paramICallback) {
        mCallback = paramICallback;
    }

    private void addCountStepListener() {
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            mStepSensorType = Sensor.TYPE_STEP_COUNTER;
            Log.v(TAG, "Sensor.TYPE_STEP_COUNTER");
            mSensorManager.registerListener(StepService.this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else if (detectorSensor != null) {
            mStepSensorType = Sensor.TYPE_STEP_DETECTOR;
            Log.v(TAG, "Sensor.TYPE_STEP_DETECTOR");
            mSensorManager.registerListener(StepService.this, detectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.v(TAG, "Count sensor not available!");
            addBasePedometerListener();
        }
    }

    private void addBasePedometerListener() {
    }

    public int getStepCount() {
        return mCurrentStep;
    }

    //Khởi tạo giá trị
    private void initTodayData() {
        db = AppDatabase.getInstance(getApplicationContext());
        mCurrentStep = db.walkingStepDao().getStep().getStep();
        updateNotification();
    }

    //Lưu lên database
    public void saveData() {
        db = AppDatabase.getInstance(getApplicationContext());
        WalkingStep ws = db.walkingStepDao().getStep();
        db.walkingStepDao().updateStep(ws.getId(), mCurrentStep);
        //SharedPreferencesWalkingStep.getInstance().put(CommonUtils.getKeyToday(), mCurrentStep);
    }

    private void initBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SHUTDOWN);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIME_TICK);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
                    case Intent.ACTION_SCREEN_ON:
                        Log.i(TAG, "screen_on");
                        break;
                    case Intent.ACTION_SCREEN_OFF:
                        Log.i(TAG, "screen_off");
                        break;
                    case Intent.ACTION_USER_PRESENT:
                        Log.i(TAG, "screen unlock");
                        break;
                    case Intent.ACTION_CLOSE_SYSTEM_DIALOGS:
                        Log.i(TAG, "receive ACTION_CLOSE_SYSTEM_DIALOGS");
                        saveData();
                        break;
                    case Intent.ACTION_SHUTDOWN:
                        Log.i(TAG, "receive ACTION_SHUTDOWN");
                        saveData();
                        break;
                    case Intent.ACTION_DATE_CHANGED:
                        Log.i(TAG, "receive ACTION_DATE_CHANGED");
                        saveData();
                        break;
                    case Intent.ACTION_TIME_CHANGED:
                        Log.i(TAG, "receive ACTION_TIME_CHANGED");
                        saveData();
                        break;
                    case Intent.ACTION_TIME_TICK:
                        Log.i(TAG, "receive ACTION_TIME_TICK");
                        saveData();
                        break;
                }
            }
        };
        registerReceiver(mBroadcastReceiver, filter);
    }


    public PendingIntent getDefaultIntent(int flags) {
        return PendingIntent.getActivity(this, 1, new Intent(), flags);
    }

    // getResources().getString(R.string.app_name)
    private void initNotification() {
        mBuilder = new NotificationCompat
                .Builder(this, NotificationChannel.CHANNEL_ID2)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Số bước hôm nay " + mCurrentStep + " bước")
                .setContentText(null)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContentIntent(getDefaultIntent(Notification.FLAG_ONGOING_EVENT))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setOngoing(true);
        Notification notification = mBuilder.build();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        startForeground(mNotifyIdStep, notification);
    }

    private void updateNotification() {
        Intent hangIntent = new Intent(this, WalkingStepActivity.class);
        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification =
                mBuilder.setContentTitle("Số bước hôm nay " + mCurrentStep + " bước")
                        .setContentText(null)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(hangPendingIntent)
                        .build();
        mNotificationManager.notify(mNotifyIdStep, notification);
        if (mCallback != null) {
            mCallback.updateUi(mCurrentStep);
        }
    }
}
