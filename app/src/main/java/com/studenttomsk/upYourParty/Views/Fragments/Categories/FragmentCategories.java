package com.studenttomsk.upYourParty.Views.Fragments.Categories;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonCategory;
import com.studenttomsk.upYourParty.Classes.SingletonListResults;
import com.studenttomsk.upYourParty.Classes.SingletonTitle;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.Fragments.AllAnnouncements.FragmentAllAnnouncement;
import com.studenttomsk.upYourParty.Classes.ProfileNumbNameTel;
import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;
import com.studenttomsk.upYourParty.Views.Main.MainActivityPresenter;
import com.studenttomsk.upYourParty.Views.Main.MainMetods;
import com.studenttomsk.upYourParty.Network.PostSearchModel;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;

public class FragmentCategories extends Fragment implements View.OnClickListener, MainMetods.View, OnFragmentInteractionListener {
    View view;
    LinearLayout birthdayCard;
    LinearLayout svadbaCard;
    LinearLayout corporativCard;
    LinearLayout detskiiUtrennikCard;
    MainActivityPresenter mainActivityPresenter;
    OnFragmentInteractionListener fragmentListener;
    Fragment selectedFragment;
    EditText searchView;
    ProgressDialog pd;
    Toast toast;
    int count;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categories, container, false);
        searchView = view.findViewById(R.id.searchView);


        birthdayCard = view.findViewById(R.id.CatDR);

        svadbaCard = view.findViewById(R.id.CatSvadba);
        corporativCard = view.findViewById(R.id.catKorporativ);
        detskiiUtrennikCard = view.findViewById(R.id.catDetskiiUtr);
        birthdayCard.setOnClickListener(this);
        svadbaCard.setOnClickListener(this);
        corporativCard.setOnClickListener(this);
        detskiiUtrennikCard.setOnClickListener(this);
        toast = Toast.makeText(getContext(),"Значение в строке поиска слишком короткое!",Toast.LENGTH_LONG);
        setDataToActivity();
        count = 0;
        mainActivityPresenter = new MainActivityPresenter(this, new PostSearchModel());
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(searchView.getText().toString().length()>=2) {
                        if(count == 0) {
                            count++;
                            SingletonTitle.getInstance().SetString(searchView.getText().toString());
                            SingletonCategory.getInstance().setCategory("");
                            mainActivityPresenter.postToSearch(new AdsSearchFilterDto(searchView.getText().toString(), "", "", "", "", "id"));
                            showDialog();
                        }
                    }
                    else{
                        toast.cancel();
                        toast = Toast.makeText(getContext(),"Значение в строке поиска слишком короткое!",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                return false;
            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.CatDR: {
                SingletonTitle.getInstance().SetString("");
                SingletonCategory.getInstance().setCategory("День рождения");
                selectedFragment = new FragmentAllAnnouncement();
                break;
            }
            case R.id.CatSvadba: {
                SingletonTitle.getInstance().SetString("");
                SingletonCategory.getInstance().setCategory("Свадьба");
                selectedFragment = new FragmentAllAnnouncement();
                break;
            }
            case R.id.catKorporativ: {
                SingletonTitle.getInstance().SetString("");
                SingletonCategory.getInstance().setCategory("Корпоратив");
                selectedFragment = new FragmentAllAnnouncement();
                break;
            }
            case R.id.catDetskiiUtr: {
                SingletonTitle.getInstance().SetString("");
                SingletonCategory.getInstance().setCategory("Детский праздник");
                selectedFragment = new FragmentAllAnnouncement();
                break;
            }
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.replace(R.id.screen_area, selectedFragment).addToBackStack(null);
        ft.commit();

    }

    @Override
    public void setInformation(ProfileNumbNameTel info) {

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
        fragmentListener.onFragmentInteraction("КАТЕГОРИИ");
        fragmentListener.setBackButton(false);
    }

    @Override
    public void toSearchResultsFragment(ArrayList<SearchAnnouncementRes> searchAnnouncementRes) {
        selectedFragment = new FragmentAllAnnouncement();
        SingletonListResults.getInstance().SetData(searchAnnouncementRes);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.replace(R.id.screen_area, selectedFragment).addToBackStack(null);
        ft.commit();
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showFailMessage(String s) {

    }

    @Override
    public void onFragmentInteraction(String text) {

    }

    @Override
    public void setBackButton(boolean state) {

    }
}
