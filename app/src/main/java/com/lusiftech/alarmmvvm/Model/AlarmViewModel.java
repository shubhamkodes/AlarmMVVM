package com.lusiftech.alarmmvvm.Model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lusiftech.alarmmvvm.DataRepository;
import com.lusiftech.alarmmvvm.RoomDatabase.Alarm;
import com.lusiftech.alarmmvvm.RvAdapter.AlarmListAdapter;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {
    private LiveData<List<Alarm>> alarmListData;
    private DataRepository dataRepository;
    public AlarmViewModel(Application application){
        super(application);
        dataRepository=new DataRepository(application);
        alarmListData=dataRepository.getAlarmList();

    }

    public void insertAlarm(Alarm alarm){
        dataRepository.insertAlarm(alarm);
    }
    public void setCheck(int id,boolean check){
        dataRepository.setChecked(id,check);
    }
    public LiveData<List<Alarm>> getAlarmListData(){
        return alarmListData;
    }
    public void deleteAlarm(int id){
        dataRepository.deleteAlarm(id);
    }
    public void updateAlarm(int id,int hr,int min){
        dataRepository.updateAlarm(id,hr,min);
    }

    public  void setSun(int id,boolean check){
        dataRepository.setSun(id,check);
    }
    public void setMon(int id,boolean check){
        dataRepository.setMon(id,check);
    }

    public  void setTues(int id,boolean check){
        dataRepository.setTues(id,check);

    }
    public  void setWed(int id,boolean check){
        dataRepository.setWed(id,check);

    }
    public  void setThurs(int id,boolean check){
        dataRepository.setThurs(id,check);

    }
    public  void setFri(int id,boolean check){
        dataRepository.setFri(id,check);

    }
    public  void setSat(int id,boolean check){
        dataRepository.setSat(id,check);

    }


}
