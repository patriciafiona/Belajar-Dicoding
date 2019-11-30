package com.path_studio.submission3.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.path_studio.submission3.Activities.DetailMovieActivity;
import com.path_studio.submission3.Activities.DetailTVActivity;
import com.path_studio.submission3.Adapters.SliderAdapter;
import com.path_studio.submission3.InternetConnectionCheck;
import com.path_studio.submission3.Models.HomeItems;
import com.path_studio.submission3.Models.SearchItems;
import com.path_studio.submission3.R;
import com.path_studio.submission3.Views.HomeViewModel;
import com.path_studio.submission3.Views.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
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
    private ProgressBar progressBar;
    private HomeViewModel homeViewModel;
    private SearchViewModel searchViewModel;

    private ImageView mDiscoverMovie01, mDiscoverMovie02, mDiscoverMovie03, mDiscoverMovie04, mDiscoverMovie05;
    private ImageView mDiscoverTV01, mDiscoverTV02, mDiscoverTV03, mDiscoverTV04, mDiscoverTV05;
    private ImageView[] listPosterMovie = new ImageView[5];
    private ImageView[] listPosterTV = new ImageView[5];

    private LinearLayout mBox01, mBox02, mBox03;
    private MaterialSearchBar mSearchBar;
    private ArrayList<String> lastSearches = new ArrayList<>();

    private SearchItems hasilSearch;

    public HomeFragment() {
        // Required empty public constructor
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

        //inisialisasi
        initiate(view);

        //set Onclick
        mMoreMovie.setOnClickListener(this);
        mMoreTvShow.setOnClickListener(this);

        //Mendapatkan bahasa sesuai pengaturan
        String language = getResources().getString(R.string.language_code);

        homeViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        homeViewModel.setMovieDiscoverData(getActivity(), language);
        homeViewModel.setTVDiscoverData(getActivity(), language);

        setVisibleGone();
        showLoading(true);

        homeViewModel.getDiscover().observe(getActivity(), new Observer<ArrayList<HomeItems>>() {
            @Override
            public void onChanged(ArrayList<HomeItems> homeItems) {
                if (homeItems != null) {
                    setDiscoverData(homeItems);
                    setVisibleTrue();
                    showLoading(false);
                }
            }
        });

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

    private void initiate(View view){
        progressBar = view.findViewById(R.id.progressBar_home);
        mMoreMovie = view.findViewById(R.id.button_selengkapnya_movie);
        mMoreTvShow = view.findViewById(R.id.button_selengkapnya_tv);

        mSlideViewPager = view.findViewById(R.id.Slider);
        mDotLayout = view.findViewById(R.id.dotsLayoutPromotion);

        //inisiasi gambar
        mDiscoverMovie01 = view.findViewById(R.id.discover_movie_01);
        mDiscoverMovie02 = view.findViewById(R.id.discover_movie_02);
        mDiscoverMovie03 = view.findViewById(R.id.discover_movie_03);
        mDiscoverMovie04 = view.findViewById(R.id.discover_movie_04);
        mDiscoverMovie05 = view.findViewById(R.id.discover_movie_05);

        listPosterMovie[0] = mDiscoverMovie01;
        listPosterMovie[1] = mDiscoverMovie02;
        listPosterMovie[2] = mDiscoverMovie03;
        listPosterMovie[3] = mDiscoverMovie04;
        listPosterMovie[4] = mDiscoverMovie05;

        mDiscoverTV01 = view.findViewById(R.id.discover_tv_01);
        mDiscoverTV02 = view.findViewById(R.id.discover_tv_02);
        mDiscoverTV03 = view.findViewById(R.id.discover_tv_03);
        mDiscoverTV04 = view.findViewById(R.id.discover_tv_04);
        mDiscoverTV05 = view.findViewById(R.id.discover_tv_05);

        listPosterTV[0] = mDiscoverTV01;
        listPosterTV[1] = mDiscoverTV02;
        listPosterTV[2] = mDiscoverTV03;
        listPosterTV[3] = mDiscoverTV04;
        listPosterTV[4] = mDiscoverTV05;

        mBox01 = view.findViewById(R.id.home_promotion_box);
        mBox02 = view.findViewById(R.id.box_Movie);
        mBox03 = view.findViewById(R.id.box_tv_show);

        mSearchBar = view.findViewById(R.id.searchBar);
        setting_search();

    }

    private void setting_search(){
        mSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == mSearchBar.BUTTON_NAVIGATION) {
                    //opening or closing a navigation drawer
                } else if (buttonCode == mSearchBar.BUTTON_BACK) {
                    mSearchBar.disableSearch();
                }
            }
        });

        mSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //proses check di list-nya
                String search = mSearchBar.getText();

                if (!TextUtils.isEmpty(search)) {
                    //set data from JSON
                    String language = getResources().getString(R.string.language_code);
                    searchViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);
                    searchViewModel.setResult(getActivity(), language, search);

                    //get data from JSON
                    searchViewModel.getSearchResult().observe(getActivity(), new Observer<ArrayList<SearchItems>>() {
                        @Override
                        public void onChanged(ArrayList<SearchItems> searchItems) {
                            if (searchItems != null) {
                                //tampilkan pada 10 result
                                hasilSearch = searchItems.get(0);
                                mSearchBar.clearSuggestions();
                                mSearchBar.updateLastSuggestions(hasilSearch.getSearchTitle());
                                if (!mSearchBar.isSuggestionsVisible()) {
                                    mSearchBar.showSuggestionsList();
                                }
                            }
                        }
                    });

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = mSearchBar.getText();
                lastSearches.add(search);
            }
        });

        mSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {

            @Override
            public void OnItemClickListener(int position, View v) {
                //go to detail movie/tv show
                Log.e("int position", String.valueOf(position));
                goToDetail(position);

            }

            @Override
            public void OnItemDeleteListener(int position, View v) {
                //
            }

        });
    }

    private void goToDetail(final int id_res){
        if (hasilSearch != null) {
            ArrayList<Integer> id_result = hasilSearch.get_result_Id();
            ArrayList<String> mediaType = hasilSearch.getMediaType();

            if(id_result != null && mediaType!= null && !id_result.isEmpty() && !mediaType.isEmpty()){
                switch (mediaType.get(id_res)){
                    case "movie":
                        Intent i = new Intent(getActivity(), DetailMovieActivity.class);
                        i.putExtra("movie_id", id_result.get(id_res));
                        startActivity(i);
                        break;
                    case "tv":
                        Intent ii = new Intent(getActivity(), DetailTVActivity.class);
                        ii.putExtra("tv_id", id_result.get(id_res));
                        startActivity(ii);
                        break;
                }
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchBar.setText("");
        mSearchBar.clearSuggestions();
        mSearchBar.hideSuggestionsList();
    }

    private void setVisibleGone(){
        mBox01.setVisibility(View.GONE);
        mBox02.setVisibility(View.GONE);
        mBox03.setVisibility(View.GONE);
        mSearchBar.setVisibility(View.GONE);
    }

    private void setVisibleTrue(){
        mBox01.setVisibility(View.VISIBLE);
        mBox02.setVisibility(View.VISIBLE);
        mBox03.setVisibility(View.VISIBLE);
        mSearchBar.setVisibility(View.VISIBLE);
    }

    private void setDiscoverData(ArrayList<HomeItems> mData){
        HomeItems homeItems = mData.get(0);

        ArrayList<String> tampung01 = homeItems.getDiscover_movie_poster();
        ArrayList<String> tampung02 = homeItems.getDiscover_tv_poster();

        if(tampung01!=null && tampung02!=null){
            for(int i = 0; i < 5 ; i++){

                Glide.with(this)
                        .load(tampung01.get(i))
                        .apply(new RequestOptions().override(200, 300))
                        .into(this.listPosterMovie[i]);

                Glide.with(this)
                        .load(tampung02.get(i))
                        .apply(new RequestOptions().override(200, 300))
                        .into(this.listPosterTV[i]);
            }
        }else{
            Log.e("Discover photo", "Array kosong");
        }

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

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
