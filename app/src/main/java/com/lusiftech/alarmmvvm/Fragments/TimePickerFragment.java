package com.lusiftech.alarmmvvm.Fragments;

import android.app.Application;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lusiftech.alarmmvvm.DataRepository;
import com.lusiftech.alarmmvvm.RoomDatabase.Alarm;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private AddClickInterface app;
    public TimePickerFragment (AddClickInterface addClickInterface){
        app=addClickInterface;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar=Calendar.getInstance();
        int hr=calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getContext(),this,hr,min,false);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Alarm alarm=new Alarm();
        alarm.setMinute(minute);
        alarm.setHourOfDay(hourOfDay);
        alarm.setChecked(true);

        app.add(alarm);
    }
    public interface AddClickInterface{
        public void add(Alarm alarm);
    }
}
