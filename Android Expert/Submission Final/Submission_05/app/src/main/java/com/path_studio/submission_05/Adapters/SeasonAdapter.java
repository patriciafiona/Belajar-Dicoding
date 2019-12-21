package com.path_studio.submission_05.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.path_studio.submission_05.Models.TVItems;
import com.path_studio.submission_05.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.TVShowViewHolder>{

    private Context mContext;

    public SeasonAdapter(Context context){
        this.mContext = context;
    }

    private ArrayList<TVItems> mData = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<TVItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tv_season_item, viewGroup, false);
        return new TVShowViewHolder(mView);
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
        ImageView seasonPoster;
        TextView seasonTitle, seasonDesc, seasonOverview;

        TVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            seasonPoster = itemView.findViewById(R.id.season_poster);
            seasonTitle = itemView.findViewById(R.id.season_title);
            seasonDesc = itemView.findViewById(R.id.season_detail);
            seasonOverview = itemView.findViewById(R.id.season_overview);
        }

        void bind(final TVItems tvItems, final TVShowViewHolder tvShowViewHolder) {

            if(!tvItems.getsPoster().equals("http://image.tmdb.org/t/p/originalnull")){
                Glide.with(tvShowViewHolder.itemView.getContext())
                        .load(tvItems.getsPoster())
                        .apply(new RequestOptions().override(200, 300))
                        .into(tvShowViewHolder.seasonPoster);
            }else{
                seasonPoster.setImageResource(R.color.colorSolidGrey);
            }


            seasonTitle.setText(tvItems.getsTitle());

            String date = tvItems.getsAirDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = null;
            try {
                parse = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(parse);

            int eps_count = tvItems.getsEpsCount();

            String seassonDetail = c.get(Calendar.YEAR) + " | " + eps_count + " Episodes.";

            seasonDesc.setText(seassonDetail);

            if(tvItems.getsOverview()!=null && !tvItems.getsOverview().equals(""))
                seasonOverview.setText(tvItems.getsOverview());
            else
                seasonOverview.setText(mContext.getResources().getString(R.string.no_overview));

        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TVItems data);
    }

}
