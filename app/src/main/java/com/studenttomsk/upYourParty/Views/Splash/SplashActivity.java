package com.studenttomsk.upYourParty.Views.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonImageProfileSocial;
import com.studenttomsk.upYourParty.Classes.SingletonMyFIO;
import com.studenttomsk.upYourParty.Network.NetworkService;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.Login.LoginActivity;
import com.studenttomsk.upYourParty.Views.Main.MainActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences savenToken;
    SharedPreferences.Editor editor;
    final String SAVED_TOKEN = "Saved_token";
    final String SAVED_REFRESH_TOKEN = "Saved_refresh";
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sendToken();

    }


    public void toMainActivity(){
        if(savenToken.getString("socialAuthorization","").equals("yes")){
            Singleton.getInstance().setSocialEnter(true);
            SingletonImageProfileSocial.getInstance().setDid(savenToken.getString("imgProfile",""));
        }
        Singleton.getInstance().SetToken(savenToken.getString(SAVED_TOKEN,""));
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("type","User");
        startActivity(intent);
        finish();
    }
    public void toMainActivityGuest(){
        Singleton.getInstance().SetToken("");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("type","User");
        startActivity(intent);
        finish();
    }

    public void sendToken(){
        savenToken = PreferenceManager.getDefaultSharedPreferences(this);
        editor = savenToken.edit();
        String token = savenToken.getString(SAVED_TOKEN,"");
        NetworkService.getInstance().getJSONApi().getSuc(token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Singleton.getInstance().setGuest(false);
                            toMainActivity();
                        }
                        else{
                            Singleton.getInstance().setGuest(true);
                            toMainActivityGuest();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Singleton.getInstance().setGuest(true);
                        toMainActivity();
                    }
                });
    }

}
