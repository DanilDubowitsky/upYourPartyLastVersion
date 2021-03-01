package com.studenttomsk.upYourParty.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.studenttomsk.upYourParty.Classes.ModelChangeRecycleWithImages;
import com.studenttomsk.upYourParty.R;

import java.util.ArrayList;

public class RecImagesAdapterChange extends RecyclerView.Adapter<RecImagesAdapterChange.RecycleImagesChangeViewHolder> {

    private ArrayList<ModelChangeRecycleWithImages> list;
    private RecImagesAdapterChange.OnItemClickListener mListener;
    Context context;

    public static class RecycleImagesChangeViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ImageView mDeleteImage;
        public RecycleImagesChangeViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycleImageToDownload);
            mDeleteImage = itemView.findViewById(R.id.deleteImageRec);
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
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
    public void setOnItemClickListener( OnItemClickListener listener){
        mListener = listener;

    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public RecImagesAdapterChange(Context context,ArrayList<ModelChangeRecycleWithImages> mrwiList){
        list = mrwiList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleImagesChangeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_with_images,parent,false);
        RecycleImagesChangeViewHolder rimv = new RecycleImagesChangeViewHolder(v, mListener);
        return rimv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleImagesChangeViewHolder holder, int position) {
        ModelChangeRecycleWithImages currItem = list.get(position);
        try {
            Glide.with(context).load(Uri.parse(currItem.getImageUri())).into(holder.imageView);
        }
        catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
