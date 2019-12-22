package com.path_studio.submission_05.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.path_studio.submission_05.Alarm_Reminder.AlarmReceiver;
import com.path_studio.submission_05.R;

public class ReminderActivity extends AppCompatActivity {

    private Switch mDaily, mRelease;
    private boolean switch_daily = false;
    private boolean switch_release = false;

    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        init();

        //check status alarm
        if(alarmReceiver.checkStatusDailyAlarm(this)){
            //true
            mDaily.setChecked(true);
        }else{
            //false
            mDaily.setChecked(false);
        }

        //set alarm onclick and listener
        setAlarm();
    }

    private void init(){
        mDaily = findViewById(R.id.switch_daily_reminder);
        mRelease = findViewById(R.id.switch_release_reminder);

        alarmReceiver = new AlarmReceiver();

    }

    private void setAlarm(){
        mDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch_daily = mDaily.isChecked();
                switch_release = mRelease.isChecked();

                if(switch_daily){
                    //kalau false, alarm di set
                    String repeatTime = "07:00";
                    String repeatMessage = getResources().getString(R.string.daily_alarm_text);
                    alarmReceiver.setRepeatingAlarm(ReminderActivity.this, AlarmReceiver.TYPE_REPEATING,
                            repeatTime, repeatMessage);
                }else{
                    //batalkan alarm (jika sudah di set)
                    alarmReceiver.cancelAlarm(ReminderActivity.this, AlarmReceiver.TYPE_REPEATING);
                }
            }
        });

        mRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch_daily = mDaily.isChecked();
                switch_release = mRelease.isChecked();

                if(switch_release){
                    //kalau true, alarm di set
                }else{
                    //batalkan alarm (jika sudah di set)
                }
            }
        });
    }
}
