package com.studenttomsk.upYourParty.Views.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.media.audiofx.DynamicsProcessing;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.studenttomsk.upYourParty.Classes.AuthorizationRequestDto;
import com.studenttomsk.upYourParty.Classes.EmailValidator;
import com.studenttomsk.upYourParty.Classes.Location;
import com.studenttomsk.upYourParty.Classes.ProfileClass;
import com.studenttomsk.upYourParty.Classes.RegistrationClass;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonForgPassEmail;
import com.studenttomsk.upYourParty.Classes.SingletonImageProfileSocial;
import com.studenttomsk.upYourParty.Network.NetworkService;
import com.studenttomsk.upYourParty.Network.PostLoginModel;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.ForgPass.FogPassActivity;
import com.studenttomsk.upYourParty.Views.Main.MainActivity;
import com.studenttomsk.upYourParty.Views.Register.RegisterActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.SharedPreferences.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginMethods.View {
    ProgressBar progressBar;
    ProgressDialog pd;
    EditText email, pass;
    Button loginBtn, refreshBtn, guest_btn;
    SharedPreferences savenToken;
    SharedPreferences.Editor editor;
    TextView registrationView, fogPass;
    final String SAVED_TOKEN = "Saved_token";
    final String SAVED_REFRESH_TOKEN = "Saved_refresh";
    Editor ed;
    Toast toast;
    LoginButton loginButtonFacebook;
    Map<String,String> map = new HashMap<>();
    LoginPresenter loginPresenter;
    CallbackManager callbackManager;
    String first_name;
    String last_name;
    String emailSocial;
    String passSocial;
    String image_url;
    CircleImageView vkBtn;
    public static final String[] VK_SCOPES = new String[]{
            VKScope.WALL,
            VKScope.FRIENDS,
            VKScope.NOTIFICATIONS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fogPass = findViewById(R.id.forgPass);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        loginButtonFacebook = findViewById(R.id.circleImageView3);
        vkBtn = findViewById(R.id.circleImageView);
        vkBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        fogPass.setOnClickListener(this);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.studenttomsk.upYourParty",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        else {
            getWindow().setStatusBarColor(Color.parseColor("#CFCFCF"));
        }
        toast =  Toast.makeText(this,"Поля должны быть заполнены",Toast.LENGTH_SHORT);
        registrationView = findViewById(R.id.registration_view);
        registrationView.setOnClickListener(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        loginPresenter = new LoginPresenter(this, new PostLoginModel());
        loginButtonFacebook.setLoginText("");
        loginButtonFacebook.setCompoundDrawables(null,null,null,null);
        loginButtonFacebook.setLogoutText("");
        loginButtonFacebook.setBackgroundResource(R.drawable.facebook);
        callbackManager = CallbackManager.Factory.create();
        loginButtonFacebook.setReadPermissions(Arrays.asList("email","public_profile","user_location"));
        VKSdk.customInitialize(this,7511970,"5.52");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        vkBtn.setClickable(true);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                VKRequest vkRequest = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,"id,first_name,last_name,city,contacts"));
                vkRequest.setPreferredLang("ru");
                vkRequest.secure = false;
                vkRequest.useSystemLanguage = false;
                vkRequest.executeWithListener(vkRequestListener);
            }
            @Override
            public void onError(VKError error) {

            }
        });
    }
    VKRequest.VKRequestListener vkRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {

            try {
                JSONArray jsonObject = (JSONArray)response.json.get("response");
                JSONObject jsonObject1 = (JSONObject)jsonObject.get(0);
                JSONObject objectCity = (JSONObject)jsonObject1.get("city");
                String firstNameVk, lastName, cityVk, passwordVk, emailVk,mobPhone;
                firstNameVk = jsonObject1.get("first_name").toString();
                lastName = jsonObject1.get("last_name").toString();
                cityVk = objectCity.get("title").toString();
                emailVk = jsonObject1.get("id").toString();
                passwordVk = jsonObject1.get("id").toString();
                mobPhone = jsonObject1.get("mobile_phone").toString();
                NetworkService.getInstance().getJSONApi().reg(new RegistrationClass(emailVk,passwordVk,new ProfileClass(firstNameVk,lastName,"",cityVk,mobPhone)))
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.isSuccessful()){
                                    showPd();
                                    loginPresenter.sendPostToLoginUser(new AuthorizationRequestDto(emailVk,passwordVk));
                                }
                                else{
                                    showPd();
                                    loginPresenter.sendPostToLoginUser(new AuthorizationRequestDto(emailVk,passwordVk));

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            super.attemptFailed(request, attemptNumber, totalAttempts);
        }

        @Override
        public void onError(VKError error) {
            super.onError(error);
        }
    };

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {

        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken == null){

            }
            else
                loadUserProfile(currentAccessToken);
        }
    };

    private void loadUserProfile(AccessToken newAccessToken){
        loginButtonFacebook.setClickable(false);
        GraphRequest graphRequest = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                     Singleton.getInstance().setSocialEnter(true);
                     first_name = object.getString("first_name");
                     last_name = object.getString("last_name");
                     emailSocial = object.getString("email");
                     passSocial = object.getString("id");
                     String city = object.getJSONObject("location").get("name").toString();
                     image_url = "https://graph.facebook.com/"+passSocial+"/picture?type=normal";
                    NetworkService.getInstance().getJSONApi().reg(new RegistrationClass(emailSocial,passSocial,new ProfileClass(first_name,last_name,"",city,"")))
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.isSuccessful()){
                                        showPd();
                                        loginPresenter.sendPostToLoginUser(new AuthorizationRequestDto(emailSocial,passSocial));
                                    }
                                    else{
                                        showPd();
                                        loginPresenter.sendPostToLoginUser(new AuthorizationRequestDto(emailSocial,passSocial));
                                        SingletonImageProfileSocial.getInstance().setDid(image_url);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle params = new Bundle();
        params.putString("fields","first_name,last_name,email,id,location,hometown");
        graphRequest.setParameters(params);
        graphRequest.executeAsync();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fogPass.setClickable(true);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.circleImageView){
            vkBtn.setClickable(false);
            VKSdk.login(this,VK_SCOPES);
            Singleton.getInstance().setSocialEnter(true);
            savenToken = PreferenceManager.getDefaultSharedPreferences(this);
            ed = savenToken.edit();
            ed.putString("socialAuthorization","yes");
            ed.commit();
        }

        if(view.getId() == R.id.forgPass){
            SingletonForgPassEmail.getInstance().setEmail(email.getText().toString());
            fogPass.setClickable(false);
            Intent intent = new Intent(this, FogPassActivity.class);
            startActivity(intent);
        }

        if(view.getId()==R.id.registration_view){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }


        if(view.getId() == R.id.loginBtn){
            Singleton.getInstance().setSocialEnter(false);
            String emailS, passS;
            emailS = email.getText().toString();
            passS = pass.getText().toString();
            if(!emailS.equals("") && !passS.equals("")) {
                loginBtn.setClickable(false);
                AuthorizationRequestDto authorizationRequestDto = new AuthorizationRequestDto(email.getText().toString(), pass.getText().toString());
                showPd();
                loginPresenter.sendPostToLoginUser(authorizationRequestDto);
            }
            else {
                toast.cancel();
                toast = Toast.makeText(this, "Поля должны быть заполнены", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }

    void toMainActivity(){
        savenToken = PreferenceManager.getDefaultSharedPreferences(this);
        editor = savenToken.edit();
        Intent intent = new Intent(this,MainActivity.class);
        Singleton.getInstance().setGuest(false);
        intent.putExtra("type","User");
        startActivity(intent);
        finish();
    }


    @Override
    public void setToSharedPreferences(Map<String, String> map) {
        savenToken = PreferenceManager.getDefaultSharedPreferences(this);
        ed = savenToken.edit();
        ed.putString(SAVED_TOKEN,"Bearer_" +map.get("token"));
        ed.putString(SAVED_REFRESH_TOKEN,map.get("token_refresh"));
        ed.commit();
        hidePd();
        Singleton.getInstance().SetToken(savenToken.getString(SAVED_TOKEN,""));
        toMainActivity();
    }

    @Override
    public void showPd() {
        pd = new ProgressDialog(this);
        pd.setTitle("Подождите");
        pd.setMessage("Вход...");
        pd.show();
    }

    @Override
    public void hidePd() {
        pd.dismiss();
    }

    @Override
    public void onResponseFailRule(String s) {
        hidePd();
        if(s.equals("")){
            toast.cancel();
            toast =  Toast.makeText(this,"Неверный логин или пароль",Toast.LENGTH_SHORT);
            toast.show();
            loginBtn.setClickable(true);
        }
        else {
            toast.cancel();
            toast =  Toast.makeText(this,"Ошибка сервера: " + s,Toast.LENGTH_SHORT);
            toast.show();
            loginBtn.setClickable(true);
        }

    }

}
