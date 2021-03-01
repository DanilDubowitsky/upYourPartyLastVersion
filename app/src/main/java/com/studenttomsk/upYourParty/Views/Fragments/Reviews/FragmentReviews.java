package com.studenttomsk.upYourParty.Views.Fragments.Reviews;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studenttomsk.upYourParty.Adapters.RecycleReviewsAdapter;
import com.studenttomsk.upYourParty.Classes.AdsReviews;
import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonDataAnnounce;
import com.studenttomsk.upYourParty.Classes.SingletonEmail;
import com.studenttomsk.upYourParty.Classes.SingletonMyEmail;
import com.studenttomsk.upYourParty.Network.NetworkService;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.Fragments.AddReview.FragmentAddReview;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentReviews extends Fragment implements View.OnClickListener, OnFragmentInteractionListener {
    View view;
    private RecyclerView recyclerView;
    private RecycleReviewsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<AdsReviews> list;
    private FloatingActionButton addRev;
    private AdsReviewDto reviews;
    private OnFragmentInteractionListener fragmentListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reviews,container,false);
        addRev = view.findViewById(R.id.add_review);
        addRev.setOnClickListener(this);

        setDataToActivity();
        loadReviews();
        String myEmail, emailAnnounce;

        if(SingletonMyEmail.getInstance().getEmail()!=null){
            myEmail = SingletonMyEmail.getInstance().getEmail();
            emailAnnounce = SingletonDataAnnounce.getInstance().getData().getEmail();
            if(myEmail.equals(emailAnnounce)){
                addRev.hide();
            }
            else{
                addRev.show();
            }
        }
        else
            addRev.show();
        if(Singleton.getInstance().isGuest()){
            addRev.hide();
        }
        return view;
    }
    public void loadReviews() {
        NetworkService.getInstance().getJSONApi().getReviews(SingletonDataAnnounce.getInstance().getData().getId())
                .enqueue(new Callback<AdsReviewDto>() {
                    @Override
                    public void onResponse(Call<AdsReviewDto> call, Response<AdsReviewDto> response) {
                        if(response.isSuccessful()) {
                            reviews = response.body();
                            setData();
                            createRecycle();
                        }
                        else{
                            setData();
                            createRecycle();
                        }
                    }

                    @Override
                    public void onFailure(Call<AdsReviewDto> call, Throwable t) {
                        setData();
                        createRecycle();
                    }
                });
    }

    public void setData() {
        list = new ArrayList<>();
        list.addAll(reviews.adsReviewList);

    }

    public void createRecycle(){
        recyclerView = view.findViewById(R.id.rec_with_reviews);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new RecycleReviewsAdapter(getContext(),list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentListener = (OnFragmentInteractionListener) context;
        }
        catch (Exception e){

        }

    }
    void setDataToActivity(){
        fragmentListener.onFragmentInteraction("ОТЗЫВЫ");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_review){
            Fragment fragment = new FragmentAddReview();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.replace(R.id.screen_area, fragment).addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void onFragmentInteraction(String text) {

    }

    @Override
    public void setBackButton(boolean state) {

    }
}
