package com.studenttomsk.upYourParty.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.studenttomsk.upYourParty.Classes.ChatsItemClass;
import com.studenttomsk.upYourParty.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleChatsAdapter extends RecyclerView.Adapter<RecycleChatsAdapter.RecycleChatsHolder> {
    ArrayList<ChatsItemClass> chatsList;
    private onItemClickListener mlistener;
    Context context;
    final private String urlImage = "http://178.170.220.39:8080/ads/image/";

    public static class RecycleChatsHolder extends RecyclerView.ViewHolder{
        public TextView fioText;
        public TextView lastMsgText;
        public TextView dateText;
        public LinearLayout cardView;
        public CircleImageView profileImg;
        public RecycleChatsHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            fioText = itemView.findViewById(R.id.FIO);
            lastMsgText = itemView.findViewById(R.id.lastMsg);
            dateText = itemView.findViewById(R.id.timeMsg);
            cardView = itemView.findViewById(R.id.cardViewId);
            profileImg = itemView.findViewById(R.id.profileDialogsImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public RecycleChatsAdapter(Context context, ArrayList<ChatsItemClass> list){
        this.context = context;
        chatsList = list;
    }
    public interface onItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mlistener = listener;
    }

    @NonNull
    @Override
    public RecycleChatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chats_item,parent,false);
        RecycleChatsHolder recycleChatsHolder = new RecycleChatsHolder(v,mlistener);
        return recycleChatsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleChatsHolder holder, int position) {
        ChatsItemClass currItem = chatsList.get(position);
        holder.fioText.setText(currItem.getFio());
        holder.lastMsgText.setText(currItem.getLastMessage());
        holder.dateText.setText(currItem.getTime());
        if(currItem.getAvatar()!=null){
            Glide.with(context).load(urlImage+currItem.getAvatar()).into(holder.profileImg);

        }
        else{
            holder.profileImg.setImageResource(R.drawable.profileimage);
        }
        if(currItem.isReadMessage()){
            holder.cardView.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.chat_item_drawable));
        }
        else{
            if(!currItem.getMyEmail().equals(currItem.getLastEmail()))
                holder.cardView.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.new_message_drawable));
        }



    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

}
