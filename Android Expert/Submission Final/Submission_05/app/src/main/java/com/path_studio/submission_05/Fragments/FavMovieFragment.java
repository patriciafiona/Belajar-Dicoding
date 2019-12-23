package com.path_studio.submission_05.Fragments;

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

import com.path_studio.submission_05.Adapters.FavouriteAdapter;
import com.path_studio.submission_05.Database.FavouriteHelper;
import com.path_studio.submission_05.Entity.Favourite;
import com.path_studio.submission_05.Helper.MappingHelper;
import com.path_studio.submission_05.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavMovieFragment extends Fragment implements LoadMoviesCallback{

    private ProgressBar progressBar;
    private RecyclerView rvFavMovie;
    private FavouriteAdapter adapter;

    private LinearLayout EmptyAlert;
    private FavouriteHelper favouriteHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fav_movie, container, false);

        EmptyAlert = view.findViewById(R.id.alert_empty_fav_movie);
        progressBar = view.findViewById(R.id.progressbar);
        rvFavMovie = view.findViewById(R.id.rv_fav_movie);
        rvFavMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavMovie.setHasFixedSize(true);

        adapter = new FavouriteAdapter(getActivity());
        rvFavMovie.setAdapter(adapter);

        favouriteHelper = FavouriteHelper.getInstance(getActivity().getApplicationContext());

        new LoadMovieAsync(favouriteHelper, this).execute();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //refresh page
        adapter = new FavouriteAdapter(getActivity());
        rvFavMovie.setAdapter(adapter);
        new LoadMovieAsync(favouriteHelper, this).execute();
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

    private static class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<Favourite>> {
        private final WeakReference<FavouriteHelper> weakfavouriteHelper;
        private final WeakReference<LoadMoviesCallback> weakCallback;
        
        private LoadMovieAsync(FavouriteHelper favouriteHelper, LoadMoviesCallback callback) {
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
            Cursor dataCursor = weakfavouriteHelper.get().queryAllMovie();
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

interface LoadMoviesCallback {
    void preExecute();
    void postExecute(ArrayList<Favourite> notes);
}