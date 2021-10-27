package com.simple.mcghealth.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.NotificationChannel;
import com.simple.mcghealth.R;
import com.simple.mcghealth.entities.MedicationTime;
import com.simple.mcghealth.entities.Medicine;
import com.simple.mcghealth.receiver.AlarmReceiver;
import com.simple.mcghealth.utils.CommonUtils;

public class Music extends Service {
    MediaPlayer mediaPlayer;
    private AppDatabase db;
    private int RESULT_CODE;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            RESULT_CODE = intent.getExtras().getInt("REQUEST_CODE");
            Log.e("In music", "Hello " + RESULT_CODE);
            if (intent.getAction().equals("init_alarm")) {
                mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                Log.e("In music - INIT", "" + RESULT_CODE);
                notification();
            } else if (intent.getAction().equals("stop_alarm")) {
                Log.e("In music - STOP", "" + RESULT_CODE);
                mediaPlayer.stop();
                stopSelf();
            }
        }
        return START_NOT_STICKY;
    }

    private void notification() {
        String str = String.valueOf(RESULT_CODE);
        String back = str.substring(8, str.length()); //id medicationtime

        db = AppDatabase.getInstance(getApplicationContext());
        MedicationTime medicationTime = db.medicationTimeDao().getTime(Integer.parseInt(back));
        Medicine medicine = db.medicineDao().findMedicine(medicationTime.getMedicineId());
        Log.e("In music", "ID MEDICATION TIME: " + back);
        Log.e("In music", "MEDICINE ID: " + medicine.getId());

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        //Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm);
        String title = "Đến giờ uống thuốc";
        String description = "Thuốc: " + medicine.getName()
                + "\n" + "Số lượng: " + medicationTime.getCount() + " v"
                + "\n" + "Thời gian: " + medicationTime.getTimeDrink()
                + "\n" + "Ghi chú: " + medicine.getNote();
        Bitmap bitmapMedicine = CommonUtils.convertByteArrayToBitmap(medicine.getImage());

        //OFF
        Intent intent1 = new Intent(this, AlarmReceiver.class);
        intent1.setAction("stop_alarm");
        intent1.putExtra("REQUEST_CODE", RESULT_CODE);
        PendingIntent pendingIntentStop = PendingIntent.getBroadcast(getApplicationContext(), RESULT_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);


        //REMIND 5MIN
        Intent intent2 = new Intent(this, AlarmReceiver.class);
        intent2.setAction("repeat_alarm");
        intent2.putExtra("REQUEST_CODE", RESULT_CODE);

        PendingIntent pendingIntentRepeat = PendingIntent.getBroadcast(getApplicationContext(), RESULT_CODE, intent2, PendingIntent.FLAG_UPDATE_CURRENT);


        //REMOTE VIEW
        /*
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.remote_view_notification_small);
        notificationLayout.setTextViewText(R.id.txtDrugName, medicine.getName() + " " + medicationTime.getCount() + " viên");
        notificationLayout.setTextViewText(R.id.txtTime, "Thời gian dùng: " + medicationTime.getTimeDrink());
        if (!medicine.getName().equals("")) {
            notificationLayout.setTextViewText(R.id.txtNote, "Thời gian dùng: " + medicationTime.getTimeDrink());
        }
        notificationLayout.setImageViewBitmap(R.id.imageDrug, bitmapMedicine);
        notificationLayout.setOnClickPendingIntent(R.id.txtOff, pendingIntentStop);
        */


        Notification notification = new NotificationCompat
                .Builder(this, NotificationChannel.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(description)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(description))
                .setSmallIcon(R.drawable.ic_baseline_medical_services_24)
                .setLargeIcon(bitmapMedicine)
                //   .setContentIntent(pendingIntentStop)
                //.setSound(uri)
                .setColor(getColor(R.color.main))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setAutoCancel(true)

                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .addAction(R.drawable.ic_baseline_medical_services_24, "Đã uống", pendingIntentStop)
                .addAction(R.drawable.ic_baseline_medical_services_24, "Nhắc lại sau 5 phút", pendingIntentRepeat)
                //.setCustomBigContentView(notificationLayout)
                .build();


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(RESULT_CODE, notification);

        startForeground(RESULT_CODE, notification);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
