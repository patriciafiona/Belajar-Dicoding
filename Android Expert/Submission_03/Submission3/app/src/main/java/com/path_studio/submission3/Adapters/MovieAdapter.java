package com.path_studio.submission3.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.path_studio.submission3.Fragments.MovieFragment;
import com.path_studio.submission3.Models.MovieItems;
import com.path_studio.submission3.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private Context mContext;

    public MovieAdapter(Context context){
        this.mContext = context;
    }

    private ArrayList<MovieItems> mData = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<MovieItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.bind(mData.get(position), movieViewHolder);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView listTitle, listDesc, listAgeRating, list_ratingText;
        RatingBar listratingBar;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            listTitle = itemView.findViewById(R.id.list_movie_title);
            listDesc = itemView.findViewById(R.id.list_movie_desc);
            listAgeRating = itemView.findViewById(R.id.list_ageRating);
            listratingBar = itemView.findViewById(R.id.list_ratingBar);
            list_ratingText = itemView.findViewById(R.id.list_ratingText);
        }

        void bind(final MovieItems movieItems, final MovieViewHolder movieViewHolder) {
            Glide.with(movieViewHolder.itemView.getContext())
                    .load(movieItems.getPoster())
                    .apply(new RequestOptions().override(200, 300))
                    .into(movieViewHolder.imgPhoto);

            listTitle.setText(movieItems.getName());

            if(movieItems.getDescription()!=null && !movieItems.getDescription().isEmpty()){
                listDesc.setText(movieItems.getDescription());
            }else{
                listDesc.setText(mContext.getResources().getString(R.string.no_translate));
            }

            //check age ratting
            if(movieItems.isAdult()){
                listAgeRating.setText(mContext.getResources().getString(R.string.age_category_17));
                listAgeRating.setBackgroundResource(R.drawable.round_corner_orange);
            }else{
                listAgeRating.setText(mContext.getResources().getString(R.string.age_category_all));
                listAgeRating.setBackgroundResource(R.drawable.round_corner_green);
            }

            //set ratting bar
            double tampung = movieItems.getRatting();
            float hasil_ratting = ((float)tampung / 2);

            listratingBar.setRating(hasil_ratting);
            list_ratingText.setText(String.valueOf(tampung));

            movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(mData.get(movieViewHolder.getAdapterPosition()));

                    //START DETAIL MOVIE ACTIVITY
                    MovieFragment.getInstance().go_to_detail(movieItems.getId());
                }
            });

        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieItems data);
    }

}
