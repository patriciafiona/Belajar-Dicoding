package com.path_studio.moviecatalogue.ui.main;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.path_studio.moviecatalogue.Adapters.AkanTayangAdapter;
import com.path_studio.moviecatalogue.Activities.DetailAT;
import com.path_studio.moviecatalogue.Movie;
import com.path_studio.moviecatalogue.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class AkanTayangFragment extends Fragment {

    private MainViewModel mViewModel;

    private AkanTayangAdapter adapter;
    private String[] dataName;
    private String[] dataDescription;
    private TypedArray dataPhoto;
    private ArrayList<Movie> movies;

    private int IndexMovie=0;

    public static AkanTayangFragment newInstance() {
        return new AkanTayangFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.akan_tayang_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //list
        ListView listView = view.findViewById(R.id.lv_list_02);
        adapter = new AkanTayangAdapter(getActivity());
        listView.setAdapter(adapter);

        prepare();
        addItem();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), movies.get(i).getName(), Toast.LENGTH_SHORT).show();
                //menampilkan datanya
                IndexMovie = i;

                //ke activity detail
                go_to_detail();
            }
        });
    }

    private void prepare() {
        dataName = getResources().getStringArray(R.array.data_akan_tayang);
        dataDescription = getResources().getStringArray(R.array.data_desc_at);
        dataPhoto = getResources().obtainTypedArray(R.array.data_photo_akan_tayang);
    }

    private void addItem() {
        movies = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            Movie movie = new Movie();
            movie.setPhoto(dataPhoto.getResourceId(i, -1));
            movie.setName(dataName[i]);
            movie.setDescription(dataDescription[i]);
            movies.add(movie);
        }
        adapter.setMovies(movies);
    }

    public void go_to_detail(){
        //direct ke halaman detail mobil
        Intent i = new Intent(getActivity(), DetailAT.class);
        i.putExtra("IndexMovie", IndexMovie);
        startActivity(i);

    }

}
