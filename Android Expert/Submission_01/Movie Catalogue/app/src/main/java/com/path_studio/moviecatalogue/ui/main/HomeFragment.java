package com.path_studio.moviecatalogue.ui.main;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.path_studio.moviecatalogue.Adapters.SedangTayangAdapter;
import com.path_studio.moviecatalogue.MainActivity;
import com.path_studio.moviecatalogue.R;
import com.path_studio.moviecatalogue.Adapters.SliderAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements View.OnClickListener{

    //untuk slider
    private SliderAdapter slider_adapter;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDosts;
    private int nCurrentPage;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    //untuk inisiasi button
    Button lengkap_st;
    Button lengkap_at;

    private MainViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //untuk button selengkapnya
        lengkap_at = view.findViewById(R.id.button_selengkapnya_at);
        lengkap_st = view.findViewById(R.id.button_selengkapnya_st);

        lengkap_at.setOnClickListener(this);
        lengkap_st.setOnClickListener(this);


        //untuk slider
        mSlideViewPager = (ViewPager) view.findViewById(R.id.Slider);
        mDotLayout = (LinearLayout) view.findViewById(R.id.dotsLayoutPromotion);

        slider_adapter = new SliderAdapter(getActivity());
        mSlideViewPager.setAdapter(slider_adapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        //bagian Timer nya
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (nCurrentPage == 4) { //karena mulai dari 0
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

    public void addDotsIndicator(int position){
        mDotLayout.removeAllViews();
        mDosts = new TextView[3];

        for(int i=0; i< mDosts.length; i++){
            mDosts[i] = new TextView(getActivity());
            mDosts[i].setText(Html.fromHtml("&#8226;",0));
            mDosts[i].setTextSize(35);
            mDosts[i].setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSolidGrey));

            mDotLayout.addView(mDosts[i]);
        }

        mDosts[position].setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDonker));

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
        FragmentManager mFragmentManager = getFragmentManager();

        switch (view.getId()){
            case R.id.button_selengkapnya_at:
                //ganti ke fragment akan tayang
                AkanTayangFragment mAkanTayangFragment = new AkanTayangFragment();

                if (mFragmentManager != null) {
                    mFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_container, mAkanTayangFragment, AkanTayangFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }

                //set active menu
                ((MainActivity)getActivity()).active_menu("AkanTayang");

                break;
            case R.id.button_selengkapnya_st:
                //ganti ke fragment sedang tayang
                SedangTayangFragment mSedangTayangFragment = new SedangTayangFragment();

                if (mFragmentManager != null) {
                    mFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_container, mSedangTayangFragment, SedangTayangFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }

                //set active menu
                ((MainActivity)getActivity()).active_menu("SedangTayang");

                break;
        }
    }
}
