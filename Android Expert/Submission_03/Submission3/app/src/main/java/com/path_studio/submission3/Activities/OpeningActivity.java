package com.path_studio.submission3.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.path_studio.submission3.InternetConnectionCheck;
import com.path_studio.submission3.R;

public class OpeningActivity extends AppCompatActivity {

    private ProgressBar mProgressbar;
    private static int TIME_OUT = 3000; //3 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        //initate
        mProgressbar = findViewById(R.id.progressBar_opening);
        showLoading(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoading(false);
                Intent i = new Intent(OpeningActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

    }

    private void showLoading(Boolean state) {
        if (state) {
            mProgressbar.setVisibility(View.VISIBLE);
        } else {
            mProgressbar.setVisibility(View.GONE);
        }
    }

}
