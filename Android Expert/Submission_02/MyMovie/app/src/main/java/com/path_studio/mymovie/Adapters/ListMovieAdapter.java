package com.path_studio.mymovie.Adapters;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.path_studio.mymovie.Models.Movie;
import com.path_studio.mymovie.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListViewHolder>{

    private ArrayList<Movie> listMovie;

    public ListMovieAdapter(ArrayList<Movie> list) {
        this.listMovie = list;
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Movie movie = listMovie.get(position);

        //set poster image
        Resources res = holder.itemView.getContext().getResources();
        TypedArray posters = res.obtainTypedArray(R.array.data_poster_movie);

        holder.imgPhoto.setImageResource(posters.getResourceId(movie.getPhoto_index(), 0));
        holder.listTitle.setText(movie.getName());
        holder.listDesc.setText(movie.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listMovie.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView listTitle, listDesc;
        ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            listTitle = itemView.findViewById(R.id.list_movie_title);
            listDesc = itemView.findViewById(R.id.list_movie_desc);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }

}
