package com.studenttomsk.upYourParty.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;

public class RecycleFavoritesAdapter extends RecyclerView.Adapter<RecycleFavoritesAdapter.RecycleViewAllAnnounceAdapter> {
    private ArrayList<SearchAnnouncementRes> recycleAnnounceItems;
    private OnItemClickListener mListener;
    private String urlImage = "http://178.170.220.39:8080/ads/image/";

    public static class RecycleViewAllAnnounceAdapter extends RecyclerView.ViewHolder{
        ImageView imageView;
        ImageView imageFav;
        TextView price;
        TextView name;
        TextView category;
        TextView description;
        public RecycleViewAllAnnounceAdapter(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageAnnounce);
            imageFav = itemView.findViewById(R.id.add_to_fav);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.nameAnnounce);
            category = itemView.findViewById(R.id.categoryOnRecycle);
            description = itemView.findViewById(R.id.descrRec);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                            itemView.setClickable(false);
                        }
                    }
                }
            });
            imageFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.setToFav(position);
                        }
                    }
                }
            });
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
        void setToFav(int position);
    }

    public RecycleFavoritesAdapter(ArrayList<SearchAnnouncementRes> rca){
        recycleAnnounceItems = rca;

    }


    @NonNull
    @Override
    public RecycleViewAllAnnounceAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_fav_item,parent,false);
        RecycleViewAllAnnounceAdapter rvaa = new RecycleViewAllAnnounceAdapter(v,mListener);
        return rvaa;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAllAnnounceAdapter holder, int position) {
        SearchAnnouncementRes currentItem = recycleAnnounceItems.get(position);
        try {
            Picasso.get().load(Uri.parse(urlImage + currentItem.getImagelist().get(0).getFilename())).fit().centerCrop().into(holder.imageView);
        }
        catch (Exception e){
            holder.imageView.setImageURI(Uri.parse("android.resource://com.studenttomsk.upYourParty/drawable/noimage"));
        }
        holder.price.setText(currentItem.getPrice());
        holder.name.setText(currentItem.getTitle());
        holder.category.setText(currentItem.getCategory().toUpperCase());
        holder.description.setText(currentItem.getAdsProfile().getDescription());
    }

    @Override
    public int getItemCount() {

        return recycleAnnounceItems.size();
    }


}
