package com.studenttomsk.upYourParty.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Classes.AllMessagesClass;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleChatAdapter extends RecyclerView.Adapter<RecycleChatAdapter.RecycleChatHolder> {
    ArrayList<AllMessagesClass> list;
    public Context context;
    final private String urlImage = "http://178.170.220.39:8080/ads/image/";
    private onItemClickListener mListener;
    public static class RecycleChatHolder extends RecyclerView.ViewHolder{
        public TextView fioText;
        public TextView msgText;
        public TextView dateText;

        public CircleImageView imgProfile;
        public ImageView imgWithMessage;
        public RecycleChatHolder(@NonNull View itemView, final onItemClickListener istener) {
            super(itemView);
            fioText = itemView.findViewById(R.id.FIOChat);
            msgText = itemView.findViewById(R.id.msg);
            dateText = itemView.findViewById(R.id.timeMsgChat);
            imgProfile = itemView.findViewById(R.id.circleImageProfileChat);
            imgWithMessage = itemView.findViewById(R.id.image_withMessage);
        }
    }

    public RecycleChatAdapter(Context context, ArrayList<AllMessagesClass> mlist){
        list = mlist;
        this.context = context;
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public RecycleChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        RecycleChatHolder rch = new RecycleChatHolder(v,mListener);
        return rch;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleChatHolder holder, int position) {
        AllMessagesClass currItem = list.get(position);
        holder.dateText.setText(currItem.getTime());
        holder.fioText.setText(currItem.getAuthPerson_from().getProfilePerson().getName()+ " " +currItem.getAuthPerson_from().getProfilePerson().getMiddleName());
        holder.msgText.setText(currItem.getMessage());
        if(currItem.getAuthPerson_from().getProfilePerson().getAvatar()!=null) {
            if(currItem.getAuthPerson_from().getProfilePerson().getAvatar().equals("")){
                holder.imgProfile.setImageResource(R.drawable.profileimage);
            }
            else {
                Glide.with(context).load(urlImage + currItem.getAuthPerson_from().getProfilePerson().getAvatar()).into(holder.imgProfile);

            }

        }
        else{
            holder.imgProfile.setImageResource(R.drawable.profileimage);
        }
        if(currItem.getImageMessage()!=null){
            if(!currItem.getImageMessage().equals("")) {
                holder.imgWithMessage.setVisibility(View.VISIBLE);
                Glide.with(context).load(urlImage + currItem.getImageMessage()).into(holder.imgWithMessage);

            }
            else {
                holder.imgWithMessage.setVisibility(View.GONE);
            }
        }
        else{
            holder.imgWithMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
