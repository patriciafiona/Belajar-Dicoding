package com.path_studio.mymovie.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.path_studio.mymovie.R;

public class HomeFragment extends Fragment implements View.OnClickListener{

    Button mMoreMovie;

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
        mMoreMovie = getView().findViewById(R.id.button_selengkapnya_movie);

        //set Onclick
        mMoreMovie.setOnClickListener(this);

        //set sesuai bahasa
        updateLanguage();
    }

    private void updateLanguage(){
        mMoreMovie.setText(getResources().getString(R.string.see_all));
    }

    @Override
    public void onClick(View view) {
        //set here
        switch (view.getId()) {
            case R.id.button_selengkapnya_movie:
                //ke halaman movie
                break;
        }
    }
}
