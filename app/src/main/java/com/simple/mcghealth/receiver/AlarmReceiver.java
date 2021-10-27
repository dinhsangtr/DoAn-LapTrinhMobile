package com.simple.mcghealth.receiver;

import static android.content.Context.ALARM_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.simple.mcghealth.services.Music;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getExtras().getInt("REQUEST_CODE", -1);
        Log.e("In receiver", "Hello " + id);

        switch (intent.getAction()) {
            case "init_alarm":
                Intent intent1 = new Intent(context, Music.class);
                intent1.setAction("init_alarm");
                intent1.putExtra("REQUEST_CODE", id);
                Log.e("In receiver - INIT ", "" + id);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent1);
                } else {
                    context.startService(intent1);
                }
                break;
            case "stop_alarm":
                Log.e("In receiver - STOP ", "" + id);
                Intent intent2 = new Intent(context, Music.class);
                intent2.setAction("stop_alarm");
                intent2.putExtra("REQUEST_CODE", id);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent2);
                } else {
                    context.startService(intent2);
                }
                break;

            case "repeat_alarm":
                if (id != -1) {
                    PendingIntent pendingIntent;
                    AlarmManager alarmManager;

                    //Hủy service trước
                    Intent intent3 = new Intent(context, Music.class);
                    intent3.setAction("stop_alarm");
                    intent3.putExtra("REQUEST_CODE", id);
                    Log.e("In receiver - CANCEL ", String.valueOf(id));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(intent3);
                    } else {
                        context.startService(intent3);
                    }

                    //Thời điểm lúc phát thông báo
                    Calendar cal = Calendar.getInstance();
                    String timeOld = cal.getTime().toString();
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
                    cal.setTimeInMillis(System.currentTimeMillis());
                    cal.add(Calendar.MINUTE, 5);

                    intent = new Intent(context, AlarmReceiver.class);
                    intent.putExtra("REQUEST_CODE", id);
                    intent.setAction("init_alarm");

                    pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

                    Log.e("TIME OLD: ",  timeOld);
                    Log.e("TIME TO REPEAT: ", cal.getTime().toString());

                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                } else {
                    Log.e("Receiver: RS CODE: ", "Null");
                }
                break;
        }
    }
}
