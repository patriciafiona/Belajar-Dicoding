package com.path_studio.mymovie.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.path_studio.mymovie.Adapters.SliderAdapter;
import com.path_studio.mymovie.R;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements View.OnClickListener{

    //untuk slider
    private SliderAdapter slider_adapter;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDosts;
    private int nCurrentPage;
    private Timer timer;
    private final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    private Button mMoreMovie, mMoreTvShow;

    private ViewPager viewPager;

    public  HomeFragment(){

    }

    public  HomeFragment (ViewPager viewPager){
        this.viewPager=viewPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //inisialisasi
        mMoreMovie = view.findViewById(R.id.button_selengkapnya_movie);
        mMoreTvShow = view.findViewById(R.id.button_selengkapnya_tv);

        mSlideViewPager = view.findViewById(R.id.Slider);
        mDotLayout = view.findViewById(R.id.dotsLayoutPromotion);

        //set Onclick
        mMoreMovie.setOnClickListener(this);
        mMoreTvShow.setOnClickListener(this);

        //set sesuai bahasa
        updateLanguage();

        //untuk slider
        slider_adapter = new SliderAdapter(getActivity());
        mSlideViewPager.setAdapter(slider_adapter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        //bagian Timer nya
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (nCurrentPage == 3) { //jika sudah melewati banya slide, balik lagi ke 0 (mulai dari  0)
                    nCurrentPage = 0;
                }
                mSlideViewPager.setCurrentItem(nCurrentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void updateLanguage(){
        mMoreMovie.setText(getResources().getString(R.string.see_all));
    }

    public void addDotsIndicator(int position){
        mDotLayout.removeAllViews();
        mDosts = new TextView[3];

        if (getActivity() != null) {
            for (int i = 0; i < mDosts.length; i++) {
                mDosts[i] = new TextView(getActivity());
                mDosts[i].setText(Html.fromHtml("&#8226;", 0));
                mDosts[i].setTextSize(35);
                mDosts[i].setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSolidGrey));

                mDotLayout.addView(mDosts[i]);
            }

            mDosts[position].setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDonker));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            //
        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            nCurrentPage = i;
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            //
        }
    };

    @Override
    public void onClick(View view) {
        //set here
        switch (view.getId()) {
            case R.id.button_selengkapnya_movie:
                //ke halaman movie
                viewPager.setCurrentItem(1);
                break;
            case R.id.button_selengkapnya_tv:
                //ke halaman tv show
                viewPager.setCurrentItem(2);
                break;
        }
    }
}
