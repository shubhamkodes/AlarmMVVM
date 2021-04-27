package com.lusiftech.alarmmvvm.Receivers;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.lusiftech.alarmmvvm.DataRepository;
import com.lusiftech.alarmmvvm.RoomDatabase.Alarm;
import com.lusiftech.alarmmvvm.RoomDatabase.AlarmRoomDatabase;
import com.lusiftech.alarmmvvm.SetAlarms;

import java.util.Calendar;
import java.util.List;

public class AlarmInitService extends BroadcastReceiver {

    SetAlarms setAlarms;
    AlarmManager alarmManager;
    List<Alarm> alarmList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context,Intent intent) {
        alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        DataRepository dataRepository=new DataRepository(context);

        AlarmRoomDatabase.getExecutorService().execute(()->{
           try{
               alarmList=dataRepository.getCheckedAlarm();
               if(alarmList==null){
                   Log.v("CODE_ME","In Executors, AlarmList is null");
               } else if(alarmList.size()==0){
                   Log.v("CODE_ME","In Executors, AlarmList size is 0");
               }
               else {
                   Log.v("CODE_ME", "In Executors, AlarmList size is- " + alarmList.size());
                   setAlarms=new SetAlarms(context);
                   if(alarmList!=null && alarmList.size()>0){
                       for(int i=0;i<alarmList.size();i++){
                           Alarm alarm=alarmList.get(i);
                           setAlarms.setAlarm(alarm);
                           Log.v("CODE_ME","Alarm set for"+alarm.getHourOfDay()+":"+alarm.getMinute());

                       }
                   }
               }

           }catch (Exception e){
               Log.v("CODE_ME","In Executors :"+e.getMessage().toString());

           }

        });



    }
}
