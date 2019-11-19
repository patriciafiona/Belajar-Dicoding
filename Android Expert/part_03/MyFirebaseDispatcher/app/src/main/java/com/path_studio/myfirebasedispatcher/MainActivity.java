package com.path_studio.myfirebasedispatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSetScheduler, btnCancelScheduler;

    private String DISPATCHER_TAG = "mydispatcher";
    private String CITY = "Jakarta";

    FirebaseJobDispatcher mDispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCancelScheduler = findViewById(R.id.btn_cancel_scheduler);
        btnSetScheduler = findViewById(R.id.btn_set_scheduler);
        btnSetScheduler.setOnClickListener(this);
        btnCancelScheduler.setOnClickListener(this);

        mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

    }

    public void startDispatcher() {

        Bundle myExtrasBundle = new Bundle();
        myExtrasBundle.putString(MyJobService.EXTRAS_CITY, CITY);

        Job myJob = mDispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag(DISPATCHER_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setTrigger(Trigger.executionWindow(0, 60))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(
                        Constraint.ON_UNMETERED_NETWORK,
                        Constraint.DEVICE_CHARGING
                )
                .setExtras(myExtrasBundle)
                .build();

        mDispatcher.mustSchedule(myJob);
    }

    public void cancelDispatcher() {
        mDispatcher.cancel(DISPATCHER_TAG);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_set_scheduler){
            startDispatcher();
            Toast.makeText(this, "Dispatcher Created", Toast.LENGTH_SHORT).show();
        }

        if (v.getId() == R.id.btn_cancel_scheduler){
            cancelDispatcher();
            Toast.makeText(this, "Dispatcher Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

}
