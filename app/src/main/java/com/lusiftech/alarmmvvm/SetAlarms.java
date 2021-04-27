package com.lusiftech.alarmmvvm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.lusiftech.alarmmvvm.Receivers.AlarmNotifReceiver;
import com.lusiftech.alarmmvvm.RoomDatabase.Alarm;
import com.lusiftech.alarmmvvm.RoomDatabase.AlarmRoomDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class SetAlarms  {
    AlarmManager alarmManager;
    Context context;
    View view;
    public SetAlarms(Context ctx, View view){
        alarmManager=(AlarmManager) ctx.getSystemService(ctx.ALARM_SERVICE);
        context=ctx;
        this.view=view;
    }
    public SetAlarms (Context context){
        alarmManager=(AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void setAlarm(Alarm alarm){
        int id=alarm.getId();


        Intent i=new Intent(context, AlarmNotifReceiver.class);
        String msg=alarm.getHourOfDay()+":"+alarm.getMinute()+"--"+alarm.getId()+": PI_ID- "+id;
        i.putExtra("HOUR",msg);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,id,i,PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,alarm.getHourOfDay());
        calendar.set(Calendar.MINUTE,alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);

        long time=System.currentTimeMillis();
        if(calendar.getTimeInMillis()>time && isTodayActive(alarm)) {
              alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
              showtime(calendar);
        }

    }

    public void disableAlarm(int id){

        Intent i=new Intent(context,AlarmNotifReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,id,i,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showtime(Calendar cal){
        String from=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
        Calendar calendar=Calendar.getInstance();
        String to=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            Date startDate = simpleDateFormat.parse(to);
            Date endDate = simpleDateFormat.parse(from);

            long difference = endDate.getTime() - startDate.getTime();
            if(difference<0)
            {
                Date dateMax = simpleDateFormat.parse("24:00:00");
                Date dateMin = simpleDateFormat.parse("00:00:00");
                difference=(dateMax.getTime() -startDate.getTime() )+(endDate.getTime()-dateMin.getTime());
            }
            int days = (int) (difference / (1000*60*60*24));
            int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
            int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
            int sec = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours) - (1000*60*min)) / (1000);

           String time="Alarm set for ";
           if(hours!=0)
               time=time+hours+" hours,";
           if(min!=0)
               time=time+min+" minutes ";
           if(sec!=0)
               time=time+sec+" seconds, ";
           time=time+"from now.";
            showSnackBar(time);

        }catch (Exception e){

        }

    }

    private void showSnackBar(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static boolean isTodayActive(Alarm alarm){
        Calendar calendar=Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_WEEK);

        switch (day){
            case 1: return alarm.getSun();
            case 2: return alarm.getMon();
            case 3: return alarm.getTues();
            case 4: return alarm.getWed();
            case 5: return alarm.getThurs();
            case 6: return alarm.getFri();
            case 7: return alarm.getSat();
        }
        return false;
    }


}
