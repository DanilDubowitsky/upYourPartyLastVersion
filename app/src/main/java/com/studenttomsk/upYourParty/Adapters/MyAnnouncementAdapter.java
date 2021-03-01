package com.studenttomsk.upYourParty.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;

import java.util.ArrayList;

public class MyAnnouncementAdapter extends RecyclerView.Adapter<MyAnnouncementAdapter.MyAnnouncementViewHolder> {
    private ArrayList<AnnounceClass> myAnnouncementItems;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onEditCkick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }

    @NonNull
    @Override
    public MyAnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_my_announcem_item,parent,false);
        MyAnnouncementViewHolder mvh = new MyAnnouncementViewHolder(view,mlistener);
        return mvh;
    }

    public MyAnnouncementAdapter(ArrayList<AnnounceClass> arrayList){
        myAnnouncementItems = arrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAnnouncementViewHolder holder, int position) {
        AnnounceClass item = myAnnouncementItems.get(position);

        holder.price.setText(String.valueOf(item.getPrice()));
        holder.category.setText(item.getCategory());
        holder.name.setText(item.getTitle());
        holder.description.setText(item.getAdsProfile().getDescription());
    }

    @Override
    public int getItemCount() {
        return myAnnouncementItems.size();
    }

    public static class MyAnnouncementViewHolder extends RecyclerView.ViewHolder{
        TextView category;
        TextView price;
        TextView name;
        private ImageView editIcon;
        private ImageView deleteIcon;
        private TextView description;
        public MyAnnouncementViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            category = itemView.findViewById(R.id.categoryOnMyAnnoun);
            price = itemView.findViewById(R.id.priceOnMyAnnounce);
            editIcon = itemView.findViewById(R.id.editIcon);
            deleteIcon = itemView.findViewById(R.id.imageView5);
            description = itemView.findViewById(R.id.descriptionMy);
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
            editIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onEditCkick(position);
                            editIcon.setClickable(false);
                        }
                    }
                }
            });
            name = itemView.findViewById(R.id.nameOnMyAnnoun);
            deleteIcon.setOnClickListener(new View.OnClickListener() {
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

}
