package com.studenttomsk.upYourParty.Views.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonCity;
import com.studenttomsk.upYourParty.Classes.SingletonEmail;
import com.studenttomsk.upYourParty.Classes.SingletonImageProfile;
import com.studenttomsk.upYourParty.Classes.SingletonImageProfileSocial;
import com.studenttomsk.upYourParty.Classes.SingletonListImagesNames;
import com.studenttomsk.upYourParty.Classes.SingletonListResults;
import com.studenttomsk.upYourParty.Classes.SingletonMyEmail;
import com.studenttomsk.upYourParty.Classes.SingletonMyFIO;
import com.studenttomsk.upYourParty.Network.PostSearchModel;
import com.studenttomsk.upYourParty.Views.Fragments.AddAnnoun.AddAnnounMethods;
import com.studenttomsk.upYourParty.Network.AddAnnounceModel;
import com.studenttomsk.upYourParty.Views.Fragments.AddAnnoun.PresenterAddAnnounce;
import com.studenttomsk.upYourParty.Views.Fragments.AllAnnouncements.FragmentAllAnnouncement;
import com.studenttomsk.upYourParty.Views.Fragments.Favorites.FavoritesFragment;
import com.studenttomsk.upYourParty.Views.Fragments.MenegerAnnoun.FragmentManagerAnnouncement;
import com.studenttomsk.upYourParty.Views.Fragments.Categories.FragmentCategories;
import com.studenttomsk.upYourParty.Views.Fragments.Messages.FragmentMessages;
import com.studenttomsk.upYourParty.Views.Fragments.Profile.FragmentProfile;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Classes.ProfileNumbNameTel;
import com.studenttomsk.upYourParty.Views.Login.LoginActivity;


import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements MainMetods.View, AddAnnounMethods.View, NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    ProgressDialog pd;
    MainActivityPresenter mainActivityPresenter;
    ActionBarDrawerToggle toggle;
    SharedPreferences savenToken;
    SharedPreferences.Editor ed;
    String tagFragment = "";
    Bundle arguments;
    int countExit;
    final String SAVED_TOKEN = "Saved_token";
    final String SAVED_REFRESH_TOKEN = "Saved_refresh";
    ActionBarDrawerToggle barDrawerToggle;
    PresenterAddAnnounce addAnnounce;
    Fragment selectedFragment;
    TextView textView;
    final private String urlImage = "http://178.170.220.39:8080/ads/image/";
    NavigationView navigationView;
    Toast toast;
    Menu menu;
    Toolbar toolbar;
    @SuppressLint({"RestrictedApi", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        countExit = 0;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textView = toolbar.findViewById(R.id.myTitleBar);
        textView.setText("КАТЕГОРИИ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        else {
            getWindow().setStatusBarColor(Color.parseColor("#CFCFCF"));
        }
        toast = Toast.makeText(this,"Необходимо авторизироваться",Toast.LENGTH_SHORT);
        addAnnounce = new PresenterAddAnnounce(this,new AddAnnounceModel());

        mainActivityPresenter = new MainActivityPresenter(this,  new PostSearchModel());

        fragmentManager = getSupportFragmentManager();
        drawerLayout = findViewById(R.id.drawer);

        navigationView = findViewById(R.id.Navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        barDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(barDrawerToggle);
        barDrawerToggle.syncState();

        mainActivityPresenter.postGetProfile();

        arguments = getIntent().getExtras();
        if(savedInstanceState == null) {
            selectedFragment = new FragmentCategories();
            replaceFragment(selectedFragment);
            navigationView.setCheckedItem(R.id.categories_nav);
        }
        menu = navigationView.getMenu();
        MenuItem itemExit = menu.findItem(R.id.itemExit);
        if (Singleton.getInstance().isGuest()) {
            itemExit.setTitle("Войти");
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        String type = arguments.get("type").toString();
        selectedFragment = null;
        switch (id) {
            case R.id.fav_nav: {
                if(!Singleton.getInstance().isGuest()) {
                    tagFragment = "Favorites";
                    selectedFragment = new FavoritesFragment();
                }
                else{
                    toast.cancel();
                    toast = Toast.makeText(this,"Необходимо авторизироваться",Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            }
            case R.id.profile_nav: {
                if(!Singleton.getInstance().isGuest()) {
                    tagFragment = "Profile";
                    selectedFragment = new FragmentProfile();
                }
                else{
                        toast.cancel();
                        toast = Toast.makeText(this, "Необходимо авторизироваться", Toast.LENGTH_SHORT);
                        toast.show();
                }

                break;
            }
            case R.id.mess_nav: {
                if(!Singleton.getInstance().isGuest()) {
                    tagFragment = "Messages";
                    selectedFragment = new FragmentMessages();
                }
                    else{
                    toast.cancel();
                    toast = Toast.makeText(this,"Необходимо авторизироваться",Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            }
            case R.id.manager_announcement:{
                if(!Singleton.getInstance().isGuest()) {
                    tagFragment = "Manager";
                    selectedFragment = new FragmentManagerAnnouncement();
                }
                else{
                    toast.cancel();
                    toast = Toast.makeText(this,"Необходимо авторизироваться",Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
            }
            case R.id.categories_nav:{
                tagFragment = "Categories";
                selectedFragment = new FragmentCategories();

                break;
            }
            case R.id.itemExit:{
                if(countExit==0) {
                    SingletonMyEmail.getInstance().clearEmail();
                    Singleton.getInstance().clearSingleton();
                    deleteSharedPreferences();
                    countExit++;
                }
                break;
            }
        }
        if (selectedFragment != null) {
            try{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (tagFragment.toString()){
                            case "Categories": {
                                textView.setText("КАТЕГОРИИ");
                                break;
                            }
                            case "Manager":{
                                textView.setText("МЕНЕДЖЕР ОБЪЯВЛЕНИЙ");
                                break;
                            }
                            case "Messages":{
                                textView.setText("СООБЩЕНИЯ");
                                break;
                            }
                            case "Profile":{
                                textView.setText("РЕДАКТИРОВАТЬ ПРОФИЛЬ");
                                break;
                            }
                            case "Favorites":{
                                textView.setText("ИЗБРАННОЕ");
                                break;
                            }
                        }
                    }
                });
            }
            catch (Exception e){

            }
            drawerLayout.closeDrawer(GravityCompat.START);
            replaceFragment(selectedFragment);
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        try{
            if(SingletonListImagesNames.getInstance().GetData().size()>0) {
                addAnnounce.deleteImagePost(SingletonListImagesNames.getInstance().GetData());
                SingletonListImagesNames.getInstance().clearSingleton();
            }
        }
        catch (Exception e){
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currFragment = fragmentManager.findFragmentById(R.id.screen_area);
        String tag = currFragment.getTag();
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {

            if (tag == null) {

                super.onBackPressed();
            }
        }
    }
    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).replace(R.id.screen_area, fragment,tagFragment).commit();
    }

    public void deleteSharedPreferences(){
        savenToken = PreferenceManager.getDefaultSharedPreferences(this);
        ed = savenToken.edit();
        ed.putString(SAVED_TOKEN,"");
        ed.putString(SAVED_REFRESH_TOKEN,"");
        ed.putString("socialAuthorization","no");
        ed.commit();
        toLoginActivity();
        finish();
    }

    public void toLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void setInformation(ProfileNumbNameTel info) {
        View v = navigationView.getHeaderView(0);
        TextView fio = v.findViewById(R.id.FIO_view);
        TextView email = v.findViewById(R.id.email_view);
        CircleImageView circleImageView = v.findViewById(R.id.profileImage_view);
        fio.setText(info.getName() + " "+info.getSurname() + " " +info.getMiddle_name() );
        SingletonMyFIO.getInstance().setFirstName(info.getName());
        SingletonMyFIO.getInstance().setLastName(info.getMiddle_name());
        SingletonCity.getInstance().setCity(info.getCity());
        SingletonMyEmail.getInstance().setEmail(info.getEmail());
        email.setText(info.getEmail());
        try {
            if (info.getAvatar() != null) {
                SingletonImageProfile.getInstance().setDid(info.getAvatar());
                Glide.with(this).load(urlImage + info.getAvatar()).into(circleImageView);
            } else {
                SingletonImageProfile.getInstance().setDid("");
                circleImageView.setImageResource(R.drawable.profileimage);
            }
        }
        catch (Exception e){
            Toast.makeText(this,"Изображение не может быть загружено",Toast.LENGTH_SHORT).show();
            SingletonImageProfile.getInstance().setDid("");
            circleImageView.setImageResource(R.drawable.profileimage);
        }

    }

    @Override
    public void toSearchResultsFragment(ArrayList<SearchAnnouncementRes> searchAnnouncementRes) {
        hideDialog();
        selectedFragment = new FragmentAllAnnouncement();
        SingletonListResults.getInstance().SetData(searchAnnouncementRes);

        replaceFragment(selectedFragment);
    }

    @Override
    public void showDialog() {
        pd = new ProgressDialog(this);
        pd.setTitle("Подождите");
        pd.setMessage("Поиск...");
        pd.show();
    }

    @Override
    public void hideDialog() {
        pd.dismiss();
    }

    @Override
    public void showFailMessage(String s) {
        hideDialog();
        Toast.makeText(this,"Ошибка соединения: "+s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFragmentInteraction(String text) {
        try{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(text.toUpperCase());
                }
            });
        }
        catch (Exception e){

        }
    }

    @Override
    public void setBackButton(boolean state) {
        if(state){
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            barDrawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.arrow_color), PorterDuff.Mode.SRC_ATOP);

            barDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        else{
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            barDrawerToggle.setDrawerIndicatorEnabled(true);
            barDrawerToggle.setToolbarNavigationClickListener(null);
        }
    }

    @Override
    public void onSuccessChange() {

    }

    @Override
    public void onSuccessDelete() {

    }

    @Override
    public void successUploadImage(Map<String, String> name) {

    }

    @Override
    public void showMessage() {

    }

    @Override
    public void onFailMessage(String message) {

    }

    @Override
    public void pdShow() {

    }

    @Override
    public void pdHied() {

    }



}
