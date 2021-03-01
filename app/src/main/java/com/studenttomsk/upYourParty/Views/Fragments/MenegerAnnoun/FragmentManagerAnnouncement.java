package com.studenttomsk.upYourParty.Views.Fragments.MenegerAnnoun;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studenttomsk.upYourParty.Adapters.MyAnnouncementAdapter;
import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonDataAnnounce;
import com.studenttomsk.upYourParty.Network.ModelManagerAnnoun;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.Fragments.AddAnnoun.FragmentAddAnnouncement;
import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;
import com.studenttomsk.upYourParty.Views.Fragments.ChangeAnnouncement.FragmentChangeAnnouncement;

import java.util.ArrayList;
import java.util.List;

public class FragmentManagerAnnouncement extends Fragment implements View.OnClickListener, ManagerAnnounMethods.ViewManagerAnnoun {
    Toolbar toolbar;
    TextView title;
    private List<AnnounceClass> listdata;
    private RecyclerView recyclerView;
    private MyAnnouncementAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<AnnounceClass> myAnnouncementItems;
    Fragment selectedFragment;
    View view;
    Button buttonAdd;
    ManagerAnnounPresenter managerAnnounPresenter;
    OnFragmentInteractionListener fragmentListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meneger_announcements,container,false);

        buttonAdd = (Button)view.findViewById(R.id.to_announcementFragment);
        buttonAdd.setOnClickListener(this);


        listdata = new ArrayList<>();
        managerAnnounPresenter = new ManagerAnnounPresenter(this,new ModelManagerAnnoun());
        managerAnnounPresenter.getMyAnnuncements(Singleton.getInstance().GetToken());
        setDataToActivity();
        return view;
    }

    public void addToRecycle(){
        myAnnouncementItems = new ArrayList<>();
        myAnnouncementItems.addAll(listdata);
        initRecycleView();
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
        fragmentListener.onFragmentInteraction("МЕНЕДЖЕР ОБЪЯВЛЕНИЙ");
        fragmentListener.setBackButton(false);
    }

    public void initRecycleView(){
        recyclerView = view.findViewById(R.id.recycleMyAnnouncements);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MyAnnouncementAdapter(myAnnouncementItems);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyAnnouncementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public void onEditCkick(int position) {
                managerAnnounPresenter.setPostAnnounceInfo(listdata.get(position).getId());
            }

            @Override
            public void onDeleteClick(int position) {
                managerAnnounPresenter.deleteMyAnnounce(new ClassId(listdata.get(position).getId()));

            }
        });
    }

    void switchFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).addToBackStack(null);
        ft.replace(R.id.screen_area, selectedFragment);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        selectedFragment = null;
            switch (v.getId()){
                case R.id.to_announcementFragment:{
                    selectedFragment = new FragmentAddAnnouncement();
                    switchFragment();
                    buttonAdd.setClickable(false);
                    break;
                }
            }
    }
    @Override
    public void onSuccessListener(List<AnnounceClass> recycleMyAnnouncementItem) {
        listdata = recycleMyAnnouncementItem;
        addToRecycle();
    }

    @Override
    public void onSuccessLoadInfoView(AnnounceClass announceClass) {
        SingletonDataAnnounce.getInstance().setData(announceClass);
        Fragment selectedFragment = new FragmentChangeAnnouncement();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).addToBackStack(null);

        ft.replace(R.id.screen_area, selectedFragment);
        ft.commit();
    }

    @Override
    public void onFailListener() {
        Toast.makeText(getContext(),"Ошибка при удалении!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessDeleteAds() {
        managerAnnounPresenter.getMyAnnuncements(Singleton.getInstance().GetToken());
        Toast.makeText(getContext(),"Объявление успешно удалено!",Toast.LENGTH_SHORT).show();
    }
}
