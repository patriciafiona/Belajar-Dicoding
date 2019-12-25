package com.path_studio.myfavouriteapp.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.path_studio.myfavouriteapp.CustomOnItemClickListener;
import com.path_studio.myfavouriteapp.Entity.Favourite;
import com.path_studio.myfavouriteapp.GotoDetail;
import com.path_studio.myfavouriteapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private final ArrayList<Favourite> listNotes = new ArrayList<>();
    private final Activity activity;
    private View view;

    public FavouriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Favourite> getListNotes() {
        return listNotes;
    }

    public void setListNotes(ArrayList<Favourite> listNotes) {

        if (listNotes.size() > 0) {
            this.listNotes.clear();
        }
        this.listNotes.addAll(listNotes);

        notifyDataSetChanged();
    }

    public void addItem(Favourite note) {
        this.listNotes.add(note);
        notifyItemInserted(listNotes.size() - 1);
    }

    public void updateItem(int position, Favourite note) {
        this.listNotes.set(position, note);
        notifyItemChanged(position, note);
    }

    public void removeItem(int position) {
        this.listNotes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listNotes.size());
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        FavouriteViewHolder fav = new FavouriteViewHolder(view);
        fav.bind(position, holder);
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDescription;
        final RatingBar tvRatting;
        final ImageView tvPoster;
        final CardView cvNote;

        FavouriteViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.fav_title);
            tvDescription = itemView.findViewById(R.id.fav_overview);
            tvRatting = itemView.findViewById(R.id.rattig_fav);
            tvPoster = itemView.findViewById(R.id.fav_poster);

            cvNote = itemView.findViewById(R.id.cv_item_note);
        }

        void bind(int position, final FavouriteViewHolder holder) {
            holder.tvTitle.setText(listNotes.get(position).getTitle());

            holder.tvDescription.setText(activity.getResources().getString(R.string.see_detail));

            //set ratting bar
            double tampung = listNotes.get(position).getRatting();
            float hasil_ratting = ((float)tampung / 2);

            tvRatting.setRating(hasil_ratting);

            Glide.with(itemView.getContext())
                    .load(listNotes.get(position).getPoster())
                    .apply(new RequestOptions().override(200, 300))
                    .into(tvPoster);

            holder.cvNote.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
                @Override
                public void onItemClicked(View view, int position) {

                    int data_id = listNotes.get(position).getData_id();
                    GotoDetail gotoDetail = new GotoDetail();

                    switch (listNotes.get(position).getType()){
                        case "movie":
                            //ke halaman detail movie
                            gotoDetail.go_to_movie(activity, data_id);
                            break;
                        case "tv_show":
                            //START DETAIL TV SHOW ACTIVITY
                            gotoDetail.go_to_tv(activity, data_id);
                            break;
                    }

                }
            }));
        }

    }
}
