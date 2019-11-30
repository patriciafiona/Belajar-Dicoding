package com.path_studio.submission3.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.path_studio.submission3.R;

public class NoInternetConnection extends AppCompatActivity implements View.OnClickListener{

    private Button mCancle, mCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);

        mCancle = findViewById(R.id.btn_cancle);
        mCheck = findViewById(R.id.btn_check);

        mCancle.setOnClickListener(this);
        mCheck.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_cancle:
                finish();
                System.exit(0);
                break;
            case R.id.btn_check:
                // User clicked OK button
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
                break;
        }
    }
}
