package com.path_studio.Submission_05.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.path_studio.Submission_05.Database.FavouriteHelper;
import com.path_studio.Submission_05.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    private FavouriteHelper favouriteHelper;
    public ViewPager viewPager;
    private static FavouriteFragment instance;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    public  FavouriteFragment (ViewPager viewPager){
        this.viewPager=viewPager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        favouriteHelper = FavouriteHelper.getInstance(getActivity().getApplicationContext());
        favouriteHelper.open();

        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        BottomNavigationView navView = view.findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(), navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        instance = this;
    }

    public static FavouriteFragment getInstance() {
        return instance;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favouriteHelper.close();
    }

}
