package com.studenttomsk.upYourParty.Views.Fragments.AllAnnouncements;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studenttomsk.upYourParty.Adapters.RecycleAllAnnounceAdarter;
import com.studenttomsk.upYourParty.Classes.AddFavorites;
import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Classes.GridSpacingItemDecoration;
import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonCategory;
import com.studenttomsk.upYourParty.Classes.SingletonCity;
import com.studenttomsk.upYourParty.Classes.SingletonDataAnnounce;
import com.studenttomsk.upYourParty.Classes.SingletonTitle;
import com.studenttomsk.upYourParty.Network.ModelAllAnnouncements;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Classes.AdsImage;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;
import com.studenttomsk.upYourParty.Network.AnnouncePageModel;
import com.studenttomsk.upYourParty.Views.Fragments.AnnouncementPage.FragmentAnnouncement;
import com.studenttomsk.upYourParty.Views.Fragments.AnnouncementPage.PresenterAnnouncePage;
import com.studenttomsk.upYourParty.Classes.ClassFavorites;
import com.studenttomsk.upYourParty.Views.Fragments.Favorites.FavoritesMethods;
import com.studenttomsk.upYourParty.Views.Fragments.Favorites.FavoritesPresenter;
import com.studenttomsk.upYourParty.Network.ModelFavorites;

import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;
import java.util.List;

public class FragmentAllAnnouncement extends Fragment implements AllAnnouncementsMethods.AllAnnounView, View.OnClickListener, FavoritesMethods.ViewFav {
    View view;
    private RecyclerView recyclerView;
    private RecycleAllAnnounceAdarter adapter;
    private GridLayoutManager layoutManager;
    ArrayList<SearchAnnouncementRes> list;
    ArrayList<SearchAnnouncementRes> listSearch;
    List<AdsImage> imageList;
    Long idFavorite;
    PresenterAnnouncePage presenter;
    FavoritesPresenter favPres;
    AdsSearchFilterDto adsSearchFilterDto;
    AllAnnounsementsPresenter allAnnounsementsPresenter;
    OnFragmentInteractionListener fragmentListener;
    int curPosition;
    Toast toast;
    Fragment fragment;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_announcement,container,false);
        presenter = new PresenterAnnouncePage(this,new AnnouncePageModel());
        if(SingletonCategory.getInstance().getCategory().equals("")){
            adsSearchFilterDto = new AdsSearchFilterDto(SingletonTitle.getInstance().GetString(), "", "", "", SingletonCategory.getInstance().getCategory(), "id");
        }
        else {
            adsSearchFilterDto = new AdsSearchFilterDto("", "", "", "", SingletonCategory.getInstance().getCategory(), "id");
        }

        favPres = new FavoritesPresenter(this,new ModelFavorites());
        fab = view.findViewById(R.id.fab_filter);
        fab.setOnClickListener(this);
        allAnnounsementsPresenter = new AllAnnounsementsPresenter(new ModelAllAnnouncements(),this);
        toast = Toast.makeText(getContext(),"Ничего не найдено",Toast.LENGTH_SHORT);
        if(!SingletonCategory.getInstance().getCategory().equals("")) {
            setDataToActivity();
        }
        list = new ArrayList<>();
        createRecycle();
        allAnnounsementsPresenter.postToSearch(adsSearchFilterDto);
        return view;
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

        fragmentListener.onFragmentInteraction(SingletonCategory.getInstance().getCategory());
        fragmentListener.setBackButton(true);
    }

    public void addItems(ArrayList<SearchAnnouncementRes> searchAnnouncementRes){
        list.clear();
        list.addAll(searchAnnouncementRes);
    }

    public void toAnnounceFragment(){
        fragment = new FragmentAnnouncement();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).addToBackStack(null);
        ft.replace(R.id.screen_area, fragment);
        ft.commit();
    }


    public void createRecycle(){

        recyclerView = view.findViewById(R.id.rec_with_announ);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(view.getContext(),2);
        adapter = new RecycleAllAnnounceAdarter(getContext(),list);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,18,false,0));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecycleAllAnnounceAdarter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                presenter.setToServer(list.get(position).getId());
            }

            @Override
            public void setToFav(int position) {
                curPosition = position;
                if (Singleton.getInstance().GetToken() != null) {
                    if (list.get(position).isMyFavorite()!=0) {
                        favPres.deleteFromFav(Singleton.getInstance().GetToken(), list.get(position).isMyFavorite());
                        list.get(position).setMyFavorite(0);
                    } else {
                        presenter.setToFavor(new AddFavorites(list.get(position).getId()), Singleton.getInstance().GetToken());
                        list.get(position).setMyFavorite(list.get(position).isMyFavorite());
                    }
                }
                else{
                    Toast.makeText(getContext(),"Вы не авторизированы",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public void showAnnounce(AnnounceClass announcementClass) {
        SingletonDataAnnounce.getInstance().setData(announcementClass);
        toAnnounceFragment();
    }

    @Override
    public void onFinishAddToFavor(ClassId id) {
        idFavorite = id.getId();
        list.get(curPosition).setMyFavorite(idFavorite);
        Toast.makeText(getContext(),"Объявление добавлено в избранное",Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void toSearchResultsFragment(ArrayList<SearchAnnouncementRes> searchAnnouncementRes) {
        addItems(searchAnnouncementRes);
        adapter.notifyDataSetChanged();
        if (list.size()==0){
            toast.cancel();
            toast = Toast.makeText(getContext(),"Ничего не найдено",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(),"Ошибка загрузки объявления",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab_filter){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View view = getLayoutInflater().inflate(R.layout.dialog_sort_layout, null);
            Button btnSortPrice = view.findViewById(R.id.sort_price_btn);
            Button btnSortCity = view.findViewById(R.id.sort_city_btn);
            mBuilder.setView(view);
            AlertDialog dialog = mBuilder.create();
            btnSortPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(SingletonCategory.getInstance().getCategory().equals("")){
                        adsSearchFilterDto = new AdsSearchFilterDto(SingletonTitle.getInstance().GetString(), "", "", "", SingletonCategory.getInstance().getCategory(), "price");
                    }
                    else {
                        adsSearchFilterDto = new AdsSearchFilterDto("", "", "", "", SingletonCategory.getInstance().getCategory(), "price");
                    }
                    allAnnounsementsPresenter.postToSearch(adsSearchFilterDto);
                    dialog.dismiss();

                }
            });
            btnSortCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(SingletonCategory.getInstance().getCategory().equals("")) {
                        adsSearchFilterDto = new AdsSearchFilterDto(SingletonTitle.getInstance().GetString(), "", "", SingletonCity.getInstance().getCity(), SingletonCategory.getInstance().getCategory(), "id");
                    }
                    else{
                        adsSearchFilterDto = new AdsSearchFilterDto("", "", "", SingletonCity.getInstance().getCity(), SingletonCategory.getInstance().getCategory(), "id");
                    }
                    allAnnounsementsPresenter.postToSearch(adsSearchFilterDto);
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    @Override
    public void showResults(ArrayList<ClassFavorites> res) {

    }

    @Override
    public void showMessageSuccess() {
        Toast.makeText(getContext(),"Объявление удалено из избранного",Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }


}
