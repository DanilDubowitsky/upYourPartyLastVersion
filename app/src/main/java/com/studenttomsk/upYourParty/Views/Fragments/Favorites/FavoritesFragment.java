package com.studenttomsk.upYourParty.Views.Fragments.Favorites;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studenttomsk.upYourParty.Adapters.RecycleFavoritesAdapter;
import com.studenttomsk.upYourParty.Classes.ClassFavorites;
import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Classes.GridSpacingItemDecoration;
import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonDataAnnounce;
import com.studenttomsk.upYourParty.Network.ModelFavorites;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.Fragments.AllAnnouncements.AllAnnouncementsMethods;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;
import com.studenttomsk.upYourParty.Network.AnnouncePageModel;
import com.studenttomsk.upYourParty.Views.Fragments.AnnouncementPage.FragmentAnnouncement;
import com.studenttomsk.upYourParty.Views.Fragments.AnnouncementPage.PresenterAnnouncePage;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment implements FavoritesMethods.ViewFav, AllAnnouncementsMethods.AllAnnounView {
    View view;
    private RecyclerView recyclerView;
    private RecycleFavoritesAdapter adapter;
    private GridLayoutManager layoutManager;
    ArrayList<SearchAnnouncementRes> list;
    PresenterAnnouncePage announcePage;
    ArrayList<ClassFavorites> listWithId;
    private ProgressDialog pd;
    private int pos;
    OnFragmentInteractionListener fragmentListener;
    Fragment fragment;
    FavoritesPresenter favoritesPresenter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favor,container,false);
        getActivity().setTitle("ИЗБРАННОЕ");
        list = new ArrayList<>();
        favoritesPresenter = new FavoritesPresenter(this,new ModelFavorites());
        favoritesPresenter.getAllFavorites(Singleton.getInstance().GetToken());
        announcePage = new PresenterAnnouncePage(this,new AnnouncePageModel());

        fragmentListener.onFragmentInteraction("ИЗБРАННОЕ");
        fragmentListener.setBackButton(false);
        return view;
    }


    void initRecycle(){

        recyclerView = view.findViewById(R.id.rec_fav);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(view.getContext(),2);
        adapter = new RecycleFavoritesAdapter(list);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,18,false,0));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecycleFavoritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                announcePage.setToServer(list.get(position).getId());
            }

            @Override
            public void setToFav(int position) {
                favoritesPresenter.deleteFromFav(Singleton.getInstance().GetToken(),listWithId.get(position).getId());
                pos = position;
                pd = new ProgressDialog(getContext());
                pd.setTitle("Подождите");
                pd.setMessage("Удаление...");
                pd.show();

            }
        });

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


    @Override
    public void showResults(ArrayList<ClassFavorites> res) {
        for(int i=0;i<res.size();i++){

            list.add(new SearchAnnouncementRes(res.get(i).getAdsPerson().getId(),res.get(i).getAdsPerson().getTitle(),res.get(i).getAdsPerson().getPrice(),res.get(i).getAdsPerson().getCity(),res.get(i).getAdsPerson().getCategory(),res.get(i).getAdsPerson().getImagelist(),res.get(i).getAdsPerson().getAdsProfile()));
        }
        listWithId = res;
        initRecycle();
    }

    @Override
    public void showMessageSuccess() {

        pd.dismiss();
        Toast.makeText(getContext(),"Объявление удалено из избранного",Toast.LENGTH_SHORT).show();
        list.remove(pos);
        adapter.notifyDataSetChanged();
        try{
            Thread.sleep(2000);
        }
        catch (Exception e){

        }
    }

    @Override
    public void showAnnounce(AnnounceClass announcementClass) {
        SingletonDataAnnounce.getInstance().setData(announcementClass);
        toAnnounceFragment();
    }

    public void toAnnounceFragment(){
        fragment = new FragmentAnnouncement();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).addToBackStack(null);
        ft.replace(R.id.screen_area, fragment);
        ft.commit();
    }

    @Override
    public void onFinishAddToFavor(ClassId id) {

    }

    @Override
    public void toSearchResultsFragment(ArrayList<SearchAnnouncementRes> searchAnnouncementRes) {

    }

    @Override
    public void showError() {

    }
}
