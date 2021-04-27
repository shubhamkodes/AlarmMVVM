package com.lusiftech.alarmmvvm.Receivers;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.lusiftech.alarmmvvm.R;
import com.lusiftech.alarmmvvm.RoomDatabase.AlarmRoomDatabase;
import com.lusiftech.alarmmvvm.SetAlarms;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class BootReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
       try{
           Intent serviceIntent=new Intent(context,AlarmInitService.class);
           PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,serviceIntent,0);
           AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
           alarmManager.setExact(AlarmManager.RTC, 10*1000,pendingIntent);

           Calendar calendar=Calendar.getInstance();
           calendar.set(Calendar.HOUR_OF_DAY,0);
           calendar.set(calendar.MINUTE,10);

           alarmManager.setInexactRepeating(AlarmManager.RTC,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
       } catch (Exception e){
           Log.d("CODE_ME",e.getMessage().toString());
       }

    }
}