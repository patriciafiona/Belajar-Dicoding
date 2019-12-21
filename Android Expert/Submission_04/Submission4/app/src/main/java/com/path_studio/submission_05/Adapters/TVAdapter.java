package com.path_studio.Submission_05.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.path_studio.Submission_05.Fragments.TvShowFragment;
import com.path_studio.Submission_05.Models.TVItems;
import com.path_studio.Submission_05.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TVShowViewHolder>{

    private Context mContext;

    public TVAdapter(Context context){
        this.mContext = context;
    }

    private ArrayList<TVItems> mData = new ArrayList<>();

    private TVAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(TVAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<TVItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TVAdapter.TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new TVAdapter.TVShowViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TVShowViewHolder tvShowViewHolder, int position) {
        tvShowViewHolder.bind(mData.get(position), tvShowViewHolder);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView listTitle, listDesc, listAgeRating, list_ratingText;
        RatingBar listratingBar;
        TextView mAgeRating;

        TVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            listTitle = itemView.findViewById(R.id.list_movie_title);
            listDesc = itemView.findViewById(R.id.list_movie_desc);
            listAgeRating = itemView.findViewById(R.id.list_ageRating);
            listratingBar = itemView.findViewById(R.id.list_ratingBar);
            list_ratingText = itemView.findViewById(R.id.list_ratingText);

            mAgeRating = itemView.findViewById(R.id.list_ageRating);
        }

        void bind(final TVItems tvItems, final TVShowViewHolder tvShowViewHolder) {
            Glide.with(tvShowViewHolder.itemView.getContext())
                    .load(tvItems.getPoster())
                    .apply(new RequestOptions().override(200, 300))
                    .into(tvShowViewHolder.imgPhoto);

            listTitle.setText(tvItems.getTitle());

            if(tvItems.getDescription()!=null && !tvItems.getDescription().isEmpty()){
                listDesc.setText(tvItems.getDescription());
            }else{
                listDesc.setText(mContext.getResources().getString(R.string.no_translate));
            }

            //set ratting bar
            double tampung = tvItems.getRatting();
            float hasil_ratting = ((float)tampung / 2);

            listratingBar.setRating(hasil_ratting);
            list_ratingText.setText(String.valueOf(tampung));

            tvShowViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(mData.get(tvShowViewHolder.getAdapterPosition()));

                    //START DETAIL TV SHOW ACTIVITY
                    TvShowFragment.getInstance().go_to_detail(tvItems.getId_TV());
                }
            });

            mAgeRating.setVisibility(View.GONE);

        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TVItems data);
    }

}
