package com.path_studio.submission_05.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        checkAlarm();

        //set alarm onclick and listener
        setAlarm();
    }

    private void init(){
        mDaily = findViewById(R.id.switch_daily_reminder);
        mRelease = findViewById(R.id.switch_release_reminder);

        alarmReceiver = new AlarmReceiver();

    }

    private void checkAlarm(){
        if(alarmReceiver.checkStatusDailyAlarm(this)){
            //true
            mDaily.setChecked(true);
        }else{
            //false
            mDaily.setChecked(false);
        }

        if(alarmReceiver.checkStatusReleaseAlarm(this)){
            mRelease.setChecked(true);
        }else{
            mRelease.setChecked(false);
        }
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
                    alarmReceiver.setRepeatingAlarmDaily(ReminderActivity.this, AlarmReceiver.TYPE_DAILY,
                            repeatTime, repeatMessage);
                }else{
                    //batalkan alarm (jika sudah di set)
                    alarmReceiver.cancelAlarm(ReminderActivity.this, AlarmReceiver.TYPE_DAILY);
                }
            }
        });

        mRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch_daily = mDaily.isChecked();
                switch_release = mRelease.isChecked();

                if(switch_release){
                    String repeatTime = "08:00";
                    alarmReceiver.setRepeatingAlarmRelease(ReminderActivity.this, AlarmReceiver.TYPE_RELEASE,
                            repeatTime);
                }else{
                    //batalkan alarm (jika sudah di set)
                    alarmReceiver.cancelAlarm(ReminderActivity.this, AlarmReceiver.TYPE_RELEASE);
                }
            }
        });
    }

}
