package com.path_studio.myfavouriteapp.Fragments;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.path_studio.myfavouriteapp.Adapters.FavouriteAdapter;
import com.path_studio.myfavouriteapp.Database.DatabaseContract;
import com.path_studio.myfavouriteapp.Entity.Favourite;
import com.path_studio.myfavouriteapp.Helper.MappingHelper;
import com.path_studio.myfavouriteapp.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavouriteMovieFragment extends Fragment implements LoadMoviesCallback {

    private ProgressBar progressBar;
    private RecyclerView rvFavMovie;
    private FavouriteAdapter adapter;

    private LinearLayout EmptyAlert;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavouriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite_movie, container, false);

        EmptyAlert = view.findViewById(R.id.alert_empty_fav_movie);
        progressBar = view.findViewById(R.id.progressbar);
        rvFavMovie = view.findViewById(R.id.rv_fav_movie);
        rvFavMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavMovie.setHasFixedSize(true);

        adapter = new FavouriteAdapter(getActivity());
        rvFavMovie.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, getContext());
        getContext().getContentResolver().registerContentObserver(DatabaseContract.FavouriteColumns.CONTENT_URI_01, true, myObserver);

        new LoadMovieAsync(getActivity(), this).execute();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //refresh page
        adapter = new FavouriteAdapter(getActivity());
        rvFavMovie.setAdapter(adapter);
        new LoadMovieAsync(getActivity(), this).execute();
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
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private LoadMovieAsync(Context context, LoadMoviesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Favourite> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseContract.FavouriteColumns.CONTENT_URI_01, null, null, null, null);
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Favourite> notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExecute(notes);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }

}

interface LoadMoviesCallback {
    void preExecute();
    void postExecute(ArrayList<Favourite> notes);
}
