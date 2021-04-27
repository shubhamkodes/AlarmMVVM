package com.lusiftech.alarmmvvm;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;

import com.lusiftech.alarmmvvm.RoomDatabase.Alarm;
import com.lusiftech.alarmmvvm.RoomDatabase.AlarmDao;
import com.lusiftech.alarmmvvm.RoomDatabase.AlarmRoomDatabase;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DataRepository {
    private static AlarmDao alarmDao;
    private static LiveData<List<Alarm>> alarmList;

    public DataRepository(Application application){
        AlarmRoomDatabase alarmRoomDatabase=AlarmRoomDatabase.getRoomDatabaseInstance(application);
        alarmDao=alarmRoomDatabase.alarmDao();
        alarmList=alarmDao.getAllAlarm();
    }

    public DataRepository(Context context){
        AlarmRoomDatabase alarmRoomDatabase=AlarmRoomDatabase.getRoomDatabaseInstance(context);
        alarmDao=alarmRoomDatabase.alarmDao();
        alarmList=alarmDao.getAllAlarm();
    }
    public void insertAlarm(Alarm alarm){
        AlarmRoomDatabase.getExecutorService().execute(()->{
            alarmDao.insert(alarm);
        });

    }
    public void deleteAlarm(int id)
    {
        AlarmRoomDatabase.getExecutorService().execute(()->{
            alarmDao.delete(id);
        });

    }
    public void setChecked(int id,boolean checked){
            AlarmRoomDatabase.getExecutorService().execute(()->{
                alarmDao.setChecked(id,checked);
            });
    }
    public LiveData<List<Alarm>> getAlarmList(){
        return alarmList;
    }

    public List<Alarm> getCheckedAlarm(){ return alarmDao.getCheckedAlarm(); }

    public void updateAlarm(int id,int hr, int min)
    {
        AlarmRoomDatabase.getExecutorService().execute(()->{
            alarmDao.updateTime(id,hr,min);
        });

    }

    public  void setSun(int id,boolean check){
        AlarmRoomDatabase.getExecutorService().execute(()->{
            alarmDao.setSun(id,check);
        });
    }

    public  void setMon(int id,boolean check){
        AlarmRoomDatabase.getExecutorService().execute(()->{
            alarmDao.setMon(id,check);
        });
    }
    public  void setTues(int id,boolean check){
       AlarmRoomDatabase.getExecutorService().execute(()->{
           alarmDao.setTues(id,check);
       });

    }
    public  void setWed(int id,boolean check){
        AlarmRoomDatabase.getExecutorService().execute(()->{
            alarmDao.setWed(id,check);
        });

    }
    public  void setThurs(int id,boolean check){
        AlarmRoomDatabase.getExecutorService().execute(()->{
            alarmDao.setThurs(id,check);
        });

    }
    public  void setFri(int id,boolean check){
       AlarmRoomDatabase.getExecutorService().execute(()->{
           alarmDao.setFri(id,check);
       });

    }
    public  void setSat(int id,boolean check){
        AlarmRoomDatabase.getExecutorService().execute(()->{
            alarmDao.setSat(id,check);
        });

    }








}
