package com.studenttomsk.upYourParty.Views.Fragments.AnnouncementPage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.studenttomsk.upYourParty.Adapters.AdapterPager;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;
import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Classes.ModelPager;
import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonCategory;
import com.studenttomsk.upYourParty.Classes.SingletonDataAnnounce;
import com.studenttomsk.upYourParty.Classes.SingletonDid;
import com.studenttomsk.upYourParty.Classes.SingletonEmail;
import com.studenttomsk.upYourParty.Classes.SingletonMyEmail;
import com.studenttomsk.upYourParty.Network.AnnouncePageModel;
import com.studenttomsk.upYourParty.Network.ModelCreateRoom;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Classes.AddFavorites;
import com.studenttomsk.upYourParty.Views.Fragments.AllAnnouncements.AllAnnouncementsMethods;
import com.studenttomsk.upYourParty.Views.Fragments.Chat.FragmentChat;
import com.studenttomsk.upYourParty.Classes.ClassFavorites;
import com.studenttomsk.upYourParty.Views.Fragments.Favorites.FavoritesMethods;
import com.studenttomsk.upYourParty.Views.Fragments.Favorites.FavoritesPresenter;
import com.studenttomsk.upYourParty.Network.ModelFavorites;
import com.studenttomsk.upYourParty.Classes.DidClass;
import com.studenttomsk.upYourParty.Classes.RoomChatDto;
import com.studenttomsk.upYourParty.Views.Fragments.Reviews.FragmentReviews;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FragmentAnnouncement extends Fragment implements View.OnClickListener, AnnouncementPageMethods.AnnouncePageView, AllAnnouncementsMethods.AllAnnounView, FavoritesMethods.ViewFav {
    View view;
    private ViewPager viewPager;
    private String urlImage = "http://37.21.255.194:8000/ads/image/";
    private AdapterPager adapterPager;
    ArrayList<ModelPager> listImg;
    PresenterCreateRoom presenterCreateRoom;
    PresenterAnnouncePage presenterAnnouncePage;
    FavoritesPresenter favoritesPresenter;
    TextView reviews, price, description, category, city, phoneNumber, rating, title, currentPhoto, allPhotos, messageToSaler;
    AnnounceClass announceClass;
    ImageView setToFav;
    RatingBar ratingBar;
    Long idFavorite;
    ScrollView scrollView;
    Fragment fragment;
    Toast toast;
    OnFragmentInteractionListener fragmentListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_announcement, container, false);
        viewPager = view.findViewById(R.id.pager_images);
        reviews = view.findViewById(R.id.reviews);
        reviews.setOnClickListener(this);
        price = view.findViewById(R.id.priceAnnounce);
        description = view.findViewById(R.id.descriptionAnnounce);
        category = view.findViewById(R.id.categoryAnnounce);
        city = view.findViewById(R.id.cityAnnounce);
        phoneNumber = view.findViewById(R.id.phoneAnnounce);
        rating = view.findViewById(R.id.graduate);
        ratingBar = view.findViewById(R.id.ratingBar);
        scrollView = view.findViewById(R.id.scrollViewAnnounce);
        ratingBar.isIndicator();
        toast = Toast.makeText(getContext(),"Объявление добавлено в избранное",Toast.LENGTH_SHORT);
        idFavorite = SingletonDataAnnounce.getInstance().getData().getMyFavorite();
        if(SingletonCategory.getInstance().getCategory()!=null) {
            if (!SingletonCategory.getInstance().getCategory().equals("")) {
                setDataToMainActivity();
            }
        }

        title = view.findViewById(R.id.titleAnnounce);
        messageToSaler = view.findViewById(R.id.textToSaler);

        if (Singleton.getInstance().isGuest()) {
                messageToSaler.setVisibility(View.GONE);
                reviews.setVisibility(View.GONE);
        }
        else{
            if(SingletonMyEmail.getInstance().getEmail()!=null) {
                if (SingletonMyEmail.getInstance().getEmail().equals(SingletonDataAnnounce.getInstance().getData().getEmail())) {
                    messageToSaler.setVisibility(View.GONE);
                }
            }
        }

        SingletonEmail.getInstance().setEmail(SingletonDataAnnounce.getInstance().getData().getEmail());
        setToFav = view.findViewById(R.id.setToFavImg);
        setToFav.setOnClickListener(this);
        messageToSaler.setOnClickListener(this);
        presenterAnnouncePage = new PresenterAnnouncePage(this,new AnnouncePageModel());
        presenterCreateRoom = new PresenterCreateRoom(this,new ModelCreateRoom());
        favoritesPresenter = new FavoritesPresenter(this,new ModelFavorites());
        setData();
        getActivity().setTitle(title.getText());
        if(SingletonDataAnnounce.getInstance().getData().isMyFavorites()!=0){
            setToFav.setImageResource(R.drawable.full_heart);
        }
        else{
            setToFav.setImageResource(R.drawable.heart);
        }
        if(Singleton.getInstance().isGuest()){
            setToFav.setVisibility(View.GONE);
        }
        fragmentListener.setBackButton(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        messageToSaler.setClickable(true);
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


    public void setData(){
        announceClass = SingletonDataAnnounce.getInstance().getData();
        price.setText(String.valueOf(announceClass.getPrice()));
        description.setText(announceClass.getDescription());
        category.setText(announceClass.getCategory());
        city.setText("Город: "+announceClass.getCity());
        phoneNumber.setText("Телефон: "+announceClass.getNumber_phone());
        if(Singleton.getInstance().isGuest()){
            phoneNumber.setVisibility(View.GONE);
        }
        DecimalFormat df = new DecimalFormat("###.#");
        double d = Double.parseDouble(announceClass.getRating());
        rating.setText(df.format(d));
        title.setText(announceClass.getTitle());
        listImg = new ArrayList<>();
        try {
            for (int i = 0; i < SingletonDataAnnounce.getInstance().getData().getAdsImages().size(); i++) {
                listImg.add(new ModelPager(urlImage + SingletonDataAnnounce.getInstance().getData().getAdsImages().get(i).getFilename()));
            }
        }
        catch (Exception e){

        }

        adapterPager = new AdapterPager(listImg,getContext());
        viewPager.setAdapter(adapterPager);
        ratingBar.setRating(Float.parseFloat(announceClass.getRating()));
        ratingBar.setIsIndicator(true);
        ratingBar.invalidate();
    }

    void setDataToMainActivity(){
        fragmentListener.onFragmentInteraction(SingletonCategory.getInstance().getCategory());

    }




    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.reviews){
            Fragment fragment = new FragmentReviews();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).addToBackStack(null);
            ft.replace(R.id.screen_area, fragment);
            ft.commit();
        }
        if(v.getId() == R.id.textToSaler){
            if(!Singleton.getInstance().isGuest()) {

                String email = announceClass.getEmail();
                presenterCreateRoom.presenterCreateRoom(new RoomChatDto(announceClass.getEmail()), Singleton.getInstance().GetToken());
                messageToSaler.setClickable(false);
            }
            else{
                toast.cancel();
                toast = Toast.makeText(getContext(),"Сначала авторизируйтесь",Toast.LENGTH_SHORT);
                toast.cancel();
            }
        }
        if(v.getId() == R.id.setToFavImg){
            if(SingletonDataAnnounce.getInstance().getData().isMyFavorites()!=0){
                favoritesPresenter.deleteFromFav(Singleton.getInstance().GetToken(), idFavorite);
                setToFav.setImageResource(R.drawable.heart);
                SingletonDataAnnounce.getInstance().getData().setMyFavorites(0);
            }
            else{
                SingletonDataAnnounce.getInstance().getData().setMyFavorites(SingletonDataAnnounce.getInstance().getData().getId());
                presenterAnnouncePage.setToFavor(new AddFavorites(SingletonDataAnnounce.getInstance().getData().getId()), Singleton.getInstance().GetToken());
                setToFav.setImageResource(R.drawable.full_heart);
            }
        }
    }

    @Override
    public void onSuccessDid(DidClass didClass) {
        SingletonDid.getInstance().setDid(didClass.getDid());
        fragment = new FragmentChat();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).addToBackStack(null);
        ft.replace(R.id.screen_area, fragment);
        ft.commit();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void showAnnounce(AnnounceClass announcementClass) {

    }

    @Override
    public void onFinishAddToFavor(ClassId id) {
        idFavorite = id.getId();
        toast.cancel();
        toast = Toast.makeText(getContext(),"Объявление добавлено в избранное",Toast.LENGTH_SHORT);
        toast.cancel();
    }

    @Override
    public void toSearchResultsFragment(ArrayList<SearchAnnouncementRes> searchAnnouncementRes) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showResults(ArrayList<ClassFavorites> res) {

    }

    @Override
    public void showMessageSuccess() {

    }
}
