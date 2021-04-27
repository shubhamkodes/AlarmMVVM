package com.lusiftech.alarmmvvm.Receivers;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.lusiftech.alarmmvvm.R;
import com.lusiftech.alarmmvvm.RoomDatabase.AlarmRoomDatabase;
import com.lusiftech.alarmmvvm.SetAlarms;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class BootReceiver extends BroadcastReceiver {
    public static final String CH_ID="BOOT_NOTIF_CHANNEL";
    private static SetAlarms setAlarms;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        AtomicInteger i= new AtomicInteger(1000);
        Toast.makeText(context, "AlarmMVVM: BootCompleted", Toast.LENGTH_SHORT).show();
        setAlarms=new SetAlarms(context);
       try{

           Intent serviceIntent=new Intent(context,AlarmInitService.class);
           PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,serviceIntent,0);
           AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
           alarmManager.setExact(AlarmManager.RTC, 10*1000,pendingIntent);

           displayNotification(context,"In Try Catch, and started AlarmInitBroadcast REceiver", i.get(), i.get() +1000);
           i.getAndIncrement();
       } catch (Exception e){
           displayNotification(context,e.getMessage().toString(),i.get(),i.get()+2000);
       }
  }

//     void onHandleIntent(Context context) {
//        try{
//            List<Alarm> alarmList;
//            AlarmRoomDatabase alarmRoomDatabase=AlarmRoomDatabase.getRoomDatabaseInstance(context);
//            AlarmDao alarmDao=alarmRoomDatabase.alarmDao();
//            alarmList=alarmDao.getCheckedAlarm();
//            if(alarmList!=null && alarmList.size()>0) {
//                for (int i = 0; i < alarmList.size(); i++) {
//                    Alarm alarm = alarmList.get(i);
//                    setAlarms.setAlarm(alarm);
//                }
//            }
//            else displayNotification(context,"AlarmList is null");
//        }catch (Exception e){
//           displayNotification(context,e.getMessage().toString());
//        }
////    }

    public void displayNotification(Context context,String msg,int tempCH_ID,int tempID){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,String.valueOf(tempCH_ID))
                .setContentText(msg)
                .setContentTitle("Ready to go.....")
                .setSmallIcon(R.drawable.icon)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
         NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
         if(Build.VERSION.SDK_INT>=26)
         {
             NotificationChannel notificationChannel=new NotificationChannel(String.valueOf(tempCH_ID),"AFTER BOOT PROCESS",NotificationManager.IMPORTANCE_HIGH);
             notificationManager.createNotificationChannel(notificationChannel);
         }
         notificationManager.notify(tempID,builder.build());

    }
}