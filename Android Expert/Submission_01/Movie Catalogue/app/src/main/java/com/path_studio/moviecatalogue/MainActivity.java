package com.path_studio.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.path_studio.moviecatalogue.ui.main.AkanTayangFragment;
import com.path_studio.moviecatalogue.ui.main.ContactFragment;
import com.path_studio.moviecatalogue.ui.main.HomeFragment;
import com.path_studio.moviecatalogue.ui.main.SedangTayangFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView Menu1, Menu2, Menu3, Menu4;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, HomeFragment.newInstance())
                    .commitNow();
        }

        Menu1 = (TextView) findViewById(R.id.menu_1);
        Menu2 = (TextView) findViewById(R.id.menu_2);
        Menu3 = (TextView) findViewById(R.id.menu_3);
        Menu4 = (TextView) findViewById(R.id.menu_4);

        //inisiasi button
        Menu1.setOnClickListener(this);
        Menu2.setOnClickListener(this);
        Menu3.setOnClickListener(this);
        Menu4.setOnClickListener(this);

        //set active menu is HomeFragment
        active_menu("Home");

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.menu_1:
                //ganti ke fragment home lagi
                HomeFragment mHomeFragment = new HomeFragment();

                transaction.replace(R.id.frame_container, mHomeFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

                //set active menu
                active_menu("Home");

                break;
            case R.id.menu_2:
                //ganti ke fragment sedang tayang
                SedangTayangFragment mSedangTayangFragment = new SedangTayangFragment();

                transaction.replace(R.id.frame_container, mSedangTayangFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

                //set active menu
                active_menu("SedangTayang");
                break;
            case R.id.menu_3:
                //ganti ke fragment akan tayang
                AkanTayangFragment mAkanTayangFragment = new AkanTayangFragment();

                transaction.replace(R.id.frame_container, mAkanTayangFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

                //set active menu
                active_menu("AkanTayang");

                break;
            case R.id.menu_4:
                //ganti ke fragment contact us
                //ganti ke fragment home lagi
                ContactFragment mContactFragment = new ContactFragment();

                transaction.replace(R.id.frame_container, mContactFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

                //set active menu
                active_menu("Contact");
                break;
        }
    }

    public void active_menu(String active){
        switch(active){
            case "Home":
                Menu1.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));

                Menu2.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                Menu3.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                Menu4.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                break;
            case "SedangTayang":
                Menu2.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));

                Menu1.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                Menu3.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                Menu4.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                break;
            case "AkanTayang":
                Menu3.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));

                Menu1.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                Menu2.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                Menu4.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                break;
            case "Contact":
                Menu4.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));

                Menu1.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                Menu2.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                Menu3.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorIdleBlue));
                break;
        }
    }

}
