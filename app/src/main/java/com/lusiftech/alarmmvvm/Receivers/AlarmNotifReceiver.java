package com.lusiftech.alarmmvvm.Receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.lusiftech.alarmmvvm.R;

import java.util.Calendar;

public class AlarmNotifReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ID="AlarmManagerID";
    private String msg;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "In On Receive", Toast.LENGTH_SHORT).show();

       if(intent!=null)
          msg=intent.getStringExtra("HOUR");

       displayNotication(context);

    }

    public void displayNotication(Context context){

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,CHANNEL_ID)
                .setContentTitle("Tring Tring, Wake Up")
                .setContentText("Wake up, You are made to show, Not so soo😴😴😴")
                .setAutoCancel(true)
                .setVibrate(new long[]{0,10000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setSmallIcon(R.drawable.icon);

        builder.setContentText(msg);

        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=26){
            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"Alarm Notification",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(6969,builder.build());
    }
}
