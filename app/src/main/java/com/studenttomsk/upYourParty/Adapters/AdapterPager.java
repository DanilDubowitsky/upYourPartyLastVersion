package com.studenttomsk.upYourParty.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.studenttomsk.upYourParty.Classes.ModelPager;
import com.studenttomsk.upYourParty.R;

import java.util.List;

public class AdapterPager extends PagerAdapter {
    private List<ModelPager> list;
    private LayoutInflater layoutInflater;
    private Context context;


    public AdapterPager(List<ModelPager> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.pager_item,container,false);
        ImageView imageView = view.findViewById(R.id.imagea_pager);


        Picasso.get().load(list.get(position).getImage()).fit().centerCrop().into(imageView);
        container.addView(view,0);

        return view;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
