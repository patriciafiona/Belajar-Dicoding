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

import com.path_studio.moviecatalogue.Activities.DetailAT;
import com.path_studio.moviecatalogue.Adapters.SedangTayangAdapter;
import com.path_studio.moviecatalogue.Activities.DetailST;
import com.path_studio.moviecatalogue.Movie;
import com.path_studio.moviecatalogue.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class SedangTayangFragment extends Fragment implements View.OnClickListener{

    private SedangTayangAdapter adapter;
    private String[] dataName;
    private String[] dataDescription;
    private TypedArray dataPhoto;
    private ArrayList<Movie> movies;

    private String[] juduls;
    private String[] descs;
    private String[] years;
    private String[] ratings;
    private String[] urls;
    private String[] link_youtubes;

    private int IndexMovie=0;


    private MainViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sedang_tayang_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //set jadi array dulu
        juduls = getResources().getStringArray(R.array.data_sedang_tayang);
        descs = getResources().getStringArray(R.array.data_desc_st);
        years = getResources().getStringArray(R.array.data_year_st);
        ratings = getResources().getStringArray(R.array.data_ratting_st);
        urls = getResources().getStringArray(R.array.link_web_st);
        link_youtubes = getResources().getStringArray(R.array.link_trailer_st);

        //list
        ListView listView = view.findViewById(R.id.lv_list_01);
        adapter = new SedangTayangAdapter(getActivity());
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

    public void go_to_detail(){
        //kirim datanya dengan PARCETABLE
        Movie movie = new Movie();
        movie.setName(juduls[IndexMovie]);
        movie.setYear(years[IndexMovie]);
        movie.setRatting(Integer.parseInt(ratings[IndexMovie]));
        movie.setDescription(descs[IndexMovie]);
        movie.setLink_web(urls[IndexMovie]);
        movie.setLink_trailer(link_youtubes[IndexMovie]);
        movie.setPhoto_index(IndexMovie); //nanti baca datanya di halaman detail

        //direct ke halaman detail mobil
        Intent i = new Intent(getActivity(), DetailST.class);
        i.putExtra(DetailAT.EXTRA_MOVIE, movie);
        startActivity(i);

    }

    private void prepare() {
        dataName = getResources().getStringArray(R.array.data_sedang_tayang);
        dataDescription = getResources().getStringArray(R.array.data_desc_st);
        dataPhoto = getResources().obtainTypedArray(R.array.data_photo_sedang_tayang);
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

    @Override
    public void onClick(View view) {

    }
}
