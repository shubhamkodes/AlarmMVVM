package com.lusiftech.alarmmvvm.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lusiftech.alarmmvvm.RoomDatabase.Alarm;
import com.lusiftech.alarmmvvm.RvAdapter.AlarmListAdapter;

import java.util.Calendar;

public class TimeUpdateFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    Alarm alarm;
    AlarmListAdapter.DeleteCheckedInterface updateTimerListener;
    public TimeUpdateFragment ( AlarmListAdapter.DeleteCheckedInterface update,Alarm alarm){
            updateTimerListener=update;
            this.alarm=alarm;
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
    public void onTimeSet(TimePicker view, int hr, int min) {
        alarm.setHourOfDay(hr);
        alarm.setMinute(min);
        updateTimerListener.updateAlarm(alarm);
    }




}
