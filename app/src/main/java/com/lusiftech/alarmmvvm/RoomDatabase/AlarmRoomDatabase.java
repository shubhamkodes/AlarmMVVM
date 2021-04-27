package com.lusiftech.alarmmvvm.RoomDatabase;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Alarm.class},version = 1,exportSchema = false)
public abstract class AlarmRoomDatabase extends RoomDatabase {
   public  abstract AlarmDao alarmDao();
    private static AlarmRoomDatabase roomDatabaseInstance;
    private static ExecutorService executorService= Executors.newFixedThreadPool(4);

    public static AlarmRoomDatabase getRoomDatabaseInstance(Application application){
        if(roomDatabaseInstance==null){
            synchronized (AlarmRoomDatabase.class){
                if(roomDatabaseInstance==null){
                    roomDatabaseInstance= Room.databaseBuilder(application.getApplicationContext(),AlarmRoomDatabase.class,"AlarmDatabase").build();
                }
            }
        }
        return roomDatabaseInstance;
    }
    public static AlarmRoomDatabase getRoomDatabaseInstance(Context context){
        if(roomDatabaseInstance==null){
            synchronized (AlarmRoomDatabase.class){
                if(roomDatabaseInstance==null){
                    roomDatabaseInstance=Room.databaseBuilder(context,AlarmRoomDatabase.class,"AlarmDatabase").build();
                }
            }
        }
        return roomDatabaseInstance;
    }
    public static ExecutorService getExecutorService() {
        if(executorService==null)
             executorService=Executors.newFixedThreadPool(4);
        return executorService;
    }
}
