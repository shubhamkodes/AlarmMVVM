package com.lusiftech.alarmmvvm.RoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Alarms")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id=0;

    private int hourOfDay;
    private int minute;
    private boolean checked;

    private boolean mon=true;
    private boolean tues=true;
    private boolean wed=true;
    private boolean thurs=true;
    private boolean fri=true;
    private boolean sat=true;
    private boolean sun=true;


    public void setMon(boolean temp){
        this.mon=temp;
    }
    public void setTues(boolean temp){
        this.tues=temp;
    }
    public void setWed(boolean temp){
        this.wed=temp;
    }
    public void setThurs(boolean temp){
        this.thurs=temp;
    }
    public void setFri(boolean temp){
        this.fri=temp;
    }
    public void setSat(boolean temp){
        this.sat=temp;
    }
    public void setSun(boolean temp){ this.sun=temp; }

    public boolean getMon(){return mon;}
    public boolean getTues(){return tues;}
    public boolean getWed(){return wed;}
    public boolean getThurs(){return thurs;}
    public boolean getFri(){return fri;}
    public boolean getSat(){return sat;}
    public boolean getSun(){return sun;}



    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public void setHourOfDay(int hr){
        this.hourOfDay=hr;
    }
    public int getHourOfDay(){
        return hourOfDay;
    }
    public void setMinute(int min){
        this.minute=min;
    }
    public int getMinute(){
        return minute;
    }
    public void setChecked(boolean check){ checked=check; }
    public boolean isChecked(){return checked;}



}
