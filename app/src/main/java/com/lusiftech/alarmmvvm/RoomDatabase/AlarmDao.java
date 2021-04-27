package com.lusiftech.alarmmvvm.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Alarm alarm);

    @Query("Delete from Alarms where id=:tempId")
    public void delete(int tempId);

    @Query("Select * from Alarms")
    public LiveData<List<Alarm>> getAllAlarm();

   @Query("Update Alarms set checked =:check where id=:tempID ")
    //@Update( onConflict = OnConflictStrategy.REPLACE)
    public void setChecked(int tempID,boolean check);

   @Query("Update Alarms set hourOfDay=:hr, minute=:min where id=:tempId")
   public void updateTime(int tempId,int hr,int min);

   @Query("Select * from Alarms where checked=1")
   public List<Alarm> getCheckedAlarm();

   @Query("Update Alarms set mon=:temp where id=:tempId")
    public void setMon(int tempId,boolean temp);

    @Query("Update Alarms set tues=:temp where id=:tempId")
    public void setTues(int tempId,boolean temp);

    @Query("Update Alarms set wed=:temp where id=:tempId")
    public void setWed(int tempId,boolean temp);

    @Query("Update Alarms set thurs=:temp where id=:tempId")
    public void setThurs(int tempId,boolean temp);

    @Query("Update Alarms set fri=:temp where id=:tempId")
    public void setFri(int tempId,boolean temp);

    @Query("Update Alarms set sat=:temp where id=:tempId")
    public void setSat(int tempId,boolean temp);

    @Query("Update Alarms set sun=:temp where id=:tempId")
    public void setSun(int tempId,boolean temp);




}

