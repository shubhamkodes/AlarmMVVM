package com.lusiftech.alarmmvvm.RvAdapter;

import android.app.Application;
import android.app.TimePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.lusiftech.alarmmvvm.DataRepository;
import com.lusiftech.alarmmvvm.Fragments.TimePickerFragment;
import com.lusiftech.alarmmvvm.Fragments.TimeUpdateFragment;
import com.lusiftech.alarmmvvm.R;
import com.lusiftech.alarmmvvm.RoomDatabase.Alarm;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmListAdapter extends ListAdapter<Alarm, AlarmListAdapter.AlarmViewHolder>  {

    private DeleteCheckedInterface checked;
    private FragmentManager fragmentManager;

    public AlarmListAdapter(DiffUtil.ItemCallback<Alarm> alarmItemCallback,DeleteCheckedInterface myIterface,FragmentManager fragmentManager){
        super(alarmItemCallback);
        checked=myIterface;
        this.fragmentManager=fragmentManager;
    }
    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_alarm,parent,false);
        return new AlarmViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
               Alarm alarm=getItem(position);
               TextView tv_time=holder.alarmCard.findViewById(R.id.tvTime);
               TextView tv_AmPm=holder.alarmCard.findViewById(R.id.tvAmPm);
               SwitchMaterial switchCompat =(SwitchMaterial)holder.alarmCard.findViewById(R.id.switchOnOff);
               ImageView deleteBtn=holder.alarmCard.findViewById(R.id.btnDelete);
               LinearLayout linearLayout=(LinearLayout) holder.alarmCard.findViewById(R.id.layout_days);
               ImageView dropDown=(ImageView) holder.alarmCard.findViewById(R.id.btnShowMore);


               setTime(tv_time,tv_AmPm,alarm.getHourOfDay(),alarm.getMinute());
               setDaysView(linearLayout,alarm);
               setSwitchChecked(tv_time,tv_AmPm,alarm);
               switchCompat.setChecked(alarm.isChecked());

               dropDown.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       setVisibility(dropDown,linearLayout,holder.alarmCard);
                   }
               });

               tv_time.setOnClickListener(new View.OnClickListener(){
                   @Override
                   public void onClick(View v) {

                               TimeUpdateFragment timeUpdateFragment=new TimeUpdateFragment(checked,alarm);
                               timeUpdateFragment.show(fragmentManager,"UpdateTimer");
                               checked.updateAlarm(alarm);
                   }
               });


               deleteBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       checked.deleteAlarm(alarm.getId());
                   }
               });

               switchCompat.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       boolean isChecked=switchCompat.isChecked();
                       checked.setCheck(alarm.getId(),isChecked,alarm);
                   }
               });
    }

        private void setSwitchChecked(TextView tv_time,TextView tv_AmPm,Alarm alarm){

            if(alarm.isChecked()){
                tv_AmPm.setTextColor(tv_AmPm.getResources().getColor(R.color.teal_200));
                tv_time.setTextColor(tv_time.getResources().getColor(R.color.teal_200));
            }else{
                tv_AmPm.setTextColor(tv_AmPm.getResources().getColor(R.color.teal_700));
                tv_time.setTextColor(tv_time.getResources().getColor(R.color.teal_700));
            }
        }

    private void setDaysView(LinearLayout layout,Alarm alarm){
        boolean days[]=new boolean[]{alarm.getSun(),alarm.getMon(),alarm.getTues(),alarm.getWed(),alarm.getThurs(),alarm.getFri(),alarm.getSat()};
        Button btn[]=new Button[]{layout.findViewById(R.id.btnSun),layout.findViewById(R.id.btnMon),layout.findViewById(R.id.btnTues),
                                    layout.findViewById(R.id.btnWed),layout.findViewById(R.id.btnThurs),layout.findViewById(R.id.btnFri),
                                    layout.findViewById(R.id.btnSat)};

        for(int i=0;i<7;i++){
            if(!days[i]){
                btn[i].setBackgroundColor(layout.getResources().getColor(R.color.black));
                btn[i].setTextColor(layout.getResources().getColor(R.color.teal_700));

            }
             else {
                 btn[i].setBackgroundColor(layout.getResources().getColor(R.color.teal_700));
                 btn[i].setTextColor(layout.getResources().getColor(R.color.teal_200));
            }

            final int pos=i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checked.setDaysChecked(pos+1,alarm.getId(),!days[pos],alarm);
                }
            });
        }


    }

    private void setVisibility(ImageView dropDown,LinearLayout linearLayout,CardView alarmCard){
        if(linearLayout.getVisibility()==alarmCard.GONE){
            linearLayout.setVisibility(alarmCard.VISIBLE);
            dropDown.setImageResource(R.drawable.arrow_up);
        }
        else{
            linearLayout.setVisibility(alarmCard.GONE);
            dropDown.setImageResource(R.drawable.arrow_down);
        }
    }

    private void setTime(TextView tvTime,TextView tvAmPm,int hr,int mint){

        String amPm=(hr<12)?"AM":"PM";
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hr);
        calendar.set(Calendar.MINUTE,mint);
        int temphr=(calendar.get(Calendar.HOUR)==0)?12:calendar.get(Calendar.HOUR);
        String min=(mint<10)?"0"+mint:String.valueOf(mint);

        tvTime.setText(temphr+":"+min);
        tvAmPm.setText(amPm);
    }



    //------------------Inner Classes Defined--------------------//


    public class AlarmViewHolder  extends RecyclerView.ViewHolder{
        CardView alarmCard;
        public AlarmViewHolder(CardView cardView){
            super(cardView);
            alarmCard=cardView;
        }
    }


    public static class AlarmItemCallBack extends DiffUtil.ItemCallback<Alarm>{
        @Override
        public boolean areItemsTheSame(@NonNull Alarm oldItem, @NonNull Alarm newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Alarm oldItem, @NonNull Alarm newItem) {
            return  oldItem.isChecked()==newItem.isChecked() && oldItem.getHourOfDay()==newItem.getHourOfDay()
                    && oldItem.getMinute()==newItem.getMinute() && oldItem.getSun()==newItem.getSun()
                    && oldItem.getMon()==newItem.getMon() && oldItem.getTues()==newItem.getTues()
                    && oldItem.getWed()==newItem.getWed() && oldItem.getThurs()==newItem.getThurs()
                    && oldItem.getFri()==newItem.getFri() && oldItem.getSat()==newItem.getSat();
        }
    }
    public interface DeleteCheckedInterface{
        public void setCheck(int id,boolean check,Alarm alarm);
        public void deleteAlarm(int id);
        public void updateAlarm(Alarm alarm);
        public void setDaysChecked(int position,int id,boolean status,Alarm alarm);
    }
}
