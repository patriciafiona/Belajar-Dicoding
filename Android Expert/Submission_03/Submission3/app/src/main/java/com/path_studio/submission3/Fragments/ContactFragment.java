package com.path_studio.submission3.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.path_studio.submission3.InternetConnectionCheck;
import com.path_studio.submission3.R;

public class ContactFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //check internet connection
        InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck();
        if(!internetConnectionCheck.isNetworkConnected(getActivity())){
            //show popup
            internetConnectionCheck.showAlertDialog(getActivity());
        }
    }

}
