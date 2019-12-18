package com.path_studio.submission4.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.path_studio.submission4.Adapters.FavouriteAdapter;
import com.path_studio.submission4.Database.FavouriteHelper;
import com.path_studio.submission4.Entity.Favourite;
import com.path_studio.submission4.Helper.MappingHelper;
import com.path_studio.submission4.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavTVShowFragment extends Fragment implements LoadTVCallback{

    private ProgressBar progressBar;
    private RecyclerView rvFavTVShow;
    private FavouriteAdapter adapter;

    private LinearLayout EmptyAlert;

    private FavouriteHelper favouriteHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavTVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav_tvshow, container, false);

        EmptyAlert = view.findViewById(R.id.alert_empty_fav_tv);
        progressBar = view.findViewById(R.id.progressbar);
        rvFavTVShow = view.findViewById(R.id.rv_fav_tvshow);
        rvFavTVShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavTVShow.setHasFixedSize(true);
        adapter = new FavouriteAdapter(getActivity());
        rvFavTVShow.setAdapter(adapter);

        favouriteHelper = FavouriteHelper.getInstance(getActivity().getApplicationContext());
        new FavTVShowFragment.LoadTVAsync(favouriteHelper, this).execute();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //refresh page
        adapter = new FavouriteAdapter(getActivity());
        rvFavTVShow.setAdapter(adapter);
        new FavTVShowFragment.LoadTVAsync(favouriteHelper, this).execute();
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public void postExecute(ArrayList<Favourite> notes) {
        progressBar.setVisibility(View.INVISIBLE);
        if (notes.size() > 0) {
            adapter.setListNotes(notes);
            EmptyAlert.setVisibility(View.INVISIBLE);
        } else {
            adapter.setListNotes(new ArrayList<Favourite>());
            EmptyAlert.setVisibility(View.VISIBLE);
        }
    }

    private static class LoadTVAsync extends AsyncTask<Void, Void, ArrayList<Favourite>> {
        private final WeakReference<FavouriteHelper> weakfavouriteHelper;
        private final WeakReference<LoadTVCallback> weakCallback;

        private LoadTVAsync(FavouriteHelper favouriteHelper, LoadTVCallback callback) {
            weakfavouriteHelper = new WeakReference<>(favouriteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Favourite> doInBackground(Void... voids) {
            Cursor dataCursor = weakfavouriteHelper.get().queryAllTV();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Favourite> notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExecute(notes);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

interface LoadTVCallback {
    void preExecute();
    void postExecute(ArrayList<Favourite> notes);
}
