package com.lusiftech.alarmmvvm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lusiftech.alarmmvvm.Fragments.TimePickerFragment;
import com.lusiftech.alarmmvvm.Model.AlarmViewModel;
import com.lusiftech.alarmmvvm.Receivers.AlarmNotifReceiver;
import com.lusiftech.alarmmvvm.RoomDatabase.Alarm;
import com.lusiftech.alarmmvvm.RvAdapter.AlarmListAdapter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerFragment.AddClickInterface, AlarmListAdapter.DeleteCheckedInterface {

    RecyclerView recyclerView;
    AlarmListAdapter alarmListAdapter;
    AlarmViewModel alarmViewModel;
    SetAlarms setAlarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAlarms=new SetAlarms(getApplicationContext(),findViewById(R.id.btnDelete));
        recyclerView=findViewById(R.id.rvAlarm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        alarmListAdapter=new AlarmListAdapter(new AlarmListAdapter.AlarmItemCallBack(),this,getSupportFragmentManager());

        alarmViewModel= new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AlarmViewModel.class);
        alarmViewModel.getAlarmListData().observe(this,words->{
            alarmListAdapter.submitList(words);
        });

        recyclerView.setAdapter(alarmListAdapter);
    }

    public void onFabClick(View view){
           TimePickerFragment timePickerFragment=new TimePickerFragment(this);
           timePickerFragment.show(getSupportFragmentManager(),"TIMERPICKER");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void add(Alarm alarm){
          alarmViewModel.insertAlarm(alarm);
          setAlarms.setAlarm(alarm);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setCheck(int id, boolean check, Alarm alarm){

        alarmViewModel.setCheck(id,check);
        if(check)
        {
            setAlarms.setAlarm(alarm);
        }else{
            setAlarms.disableAlarm(id);
        }
    }
    public void deleteAlarm(int id){
           setAlarms.disableAlarm(id);
           alarmViewModel.deleteAlarm(id);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateAlarm(Alarm updatedalarm){
            alarmViewModel.updateAlarm(updatedalarm.getId(),updatedalarm.getHourOfDay(),updatedalarm.getMinute());
            if(!updatedalarm.isChecked())
                 alarmViewModel.setCheck(updatedalarm.getId(),true);
            setAlarms.disableAlarm(updatedalarm.getId());             //disable old alarm
            setAlarms.setAlarm(updatedalarm);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDaysChecked(int pos, int id, boolean status, Alarm alarm){
            switch (pos){
                case 1:alarmViewModel.setSun(id,status);   cancelOrSetAlarm(alarm,status); break;
                case 2:alarmViewModel.setMon(id,status);   cancelOrSetAlarm(alarm,status); break;
                case 3:alarmViewModel.setTues(id,status);  cancelOrSetAlarm(alarm,status); break;
                case 4:alarmViewModel.setWed(id,status);   cancelOrSetAlarm(alarm,status); break;
                case 5:alarmViewModel.setThurs(id,status); cancelOrSetAlarm(alarm,status); break;
                case 6:alarmViewModel.setFri(id,status);   cancelOrSetAlarm(alarm,status); break;
                case 7:alarmViewModel.setSat(id,status);   cancelOrSetAlarm(alarm,status); break;
            }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cancelOrSetAlarm(Alarm alarm, boolean status){
          if(!SetAlarms.isTodayActive(alarm)) {
              if (status)
                  setAlarms.setAlarm(alarm);
              else
                  setAlarms.disableAlarm(alarm.getId());
          }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mItemSetting){
            //start setting activity
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}