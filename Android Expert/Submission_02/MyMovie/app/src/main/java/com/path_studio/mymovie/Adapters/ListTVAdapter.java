package com.path_studio.mymovie.Adapters;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.path_studio.mymovie.Fragments.MovieFragment;
import com.path_studio.mymovie.Fragments.TvShowFragment;
import com.path_studio.mymovie.Models.Movie;
import com.path_studio.mymovie.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListTVAdapter extends RecyclerView.Adapter<ListTVAdapter.ListViewHolder> {

    private long Index = 0;

    private ArrayList<Movie> listTV;

    public ListTVAdapter(ArrayList<Movie> list) {
        this.listTV = list;
    }

    private ListTVAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(ListTVAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListTVAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_tv, viewGroup, false);
        return new ListTVAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListTVAdapter.ListViewHolder holder, int position) {
        Movie movie = listTV.get(position);

        //set poster image
        Resources res = holder.itemView.getContext().getResources();
        TypedArray posters = res.obtainTypedArray(R.array.data_poster_tv);

        holder.imgPhoto.setImageResource(posters.getResourceId(movie.getPhoto_index(), 0));
        holder.listTitle.setText(movie.getName());
        holder.listDesc.setText(movie.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listTV.get(holder.getAdapterPosition()));

                Log.e("Item Selected",String.valueOf(holder.getAdapterPosition()));

                TvShowFragment.getInstance().go_to_detail(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTV.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView listTitle, listDesc;
        ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo_tv);
            listTitle = itemView.findViewById(R.id.list_tv_title);
            listDesc = itemView.findViewById(R.id.list_tv_desc);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }

}
