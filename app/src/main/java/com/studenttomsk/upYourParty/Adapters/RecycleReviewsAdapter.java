package com.studenttomsk.upYourParty.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Classes.AdsReviews;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleReviewsAdapter extends RecyclerView.Adapter<RecycleReviewsAdapter.RecycleReviewsHolder> {
    List<AdsReviews> dataList;
    private OnItemClickListener listener;
    Context context;
    final private String urlImage = "http://37.21.255.194:8000/ads/image/";
    @NonNull
    @Override
    public RecycleReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_reviews_item,parent,false);
        RecycleReviewsHolder rrh = new RecycleReviewsHolder(view,listener);
        return rrh;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        listener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleReviewsHolder holder, int position) {
        AdsReviews item = dataList.get(position);


        holder.fio.setText(item.getAuthPerson().getProfilePerson().getName() +" "+item.getAuthPerson().getProfilePerson().getMiddleName());
        holder.review.setText(item.getContent());
        holder.date.setText(item.getDate());
        holder.ratingBar.setRating(Float.parseFloat(item.getRating()));
        if(item.getAuthPerson().getProfilePerson().getAvatar()!=null){
            Glide.with(context).load(urlImage+item.getAuthPerson().getProfilePerson().getAvatar()).into(holder.profileImgRev);

        }
        else{
            holder.profileImgRev.setImageResource(R.drawable.profileimage);
        }
        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.review.getMaxLines()==2) {
                    holder.review.setMaxLines(30);
                }
                else{
                    holder.review.setMaxLines(2);
                }
            }
        });
    }
    public RecycleReviewsAdapter(Context context, List<AdsReviews>reviewsData){
        dataList = reviewsData;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class RecycleReviewsHolder extends RecyclerView.ViewHolder{
        TextView fio;
        TextView review;
        RatingBar ratingBar;
        TextView date;

        CircleImageView profileImgRev;

        public RecycleReviewsHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            profileImgRev = itemView.findViewById(R.id.image_profile_reviews);
            fio = itemView.findViewById(R.id.fio_review);
            review = itemView.findViewById(R.id.review);
            ratingBar = itemView.findViewById(R.id.ratingBarReviewItem);

            date = itemView.findViewById(R.id.dateReview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);

                        }
                    }
                }
            });
        }
    }
}
