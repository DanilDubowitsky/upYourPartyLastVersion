package com.studenttomsk.upYourParty.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Classes.ModelRecycleWithImages;

import java.util.ArrayList;

public class RecycleImagesAdapter extends RecyclerView.Adapter<RecycleImagesAdapter.RecycleImagesViewHolder> {
    private ArrayList<ModelRecycleWithImages> list;
    private OnItemClickListener mListener;

    public static class RecycleImagesViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ImageView mDeleteImage;
        public RecycleImagesViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
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
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public RecycleImagesAdapter(ArrayList<ModelRecycleWithImages> mrwiList){
        list = mrwiList;
    }

    @NonNull
    @Override
    public RecycleImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_with_images,parent,false);
        RecycleImagesViewHolder rimv = new RecycleImagesViewHolder(v, mListener);
        return rimv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleImagesViewHolder holder, int position) {
        ModelRecycleWithImages currItem = list.get(position);
        holder.imageView.setImageURI(currItem.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
