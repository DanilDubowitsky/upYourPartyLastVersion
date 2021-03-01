package com.studenttomsk.upYourParty.Views.Register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.studenttomsk.upYourParty.Classes.AuthorizationRequestDto;
import com.studenttomsk.upYourParty.Classes.EmailValidator;
import com.studenttomsk.upYourParty.Classes.FIOValidator;
import com.studenttomsk.upYourParty.Classes.PasswordValidator;
import com.studenttomsk.upYourParty.Classes.PhoneNumberValidator;
import com.studenttomsk.upYourParty.Classes.ProfileClass;
import com.studenttomsk.upYourParty.Classes.RegistrationClass;
import com.studenttomsk.upYourParty.Classes.SingletonCity;
import com.studenttomsk.upYourParty.Network.NetworkService;
import com.studenttomsk.upYourParty.Network.PostLoginModel;
import com.studenttomsk.upYourParty.Network.PostRegisterModel;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.Login.LoginActivity;
import com.studenttomsk.upYourParty.Views.Login.LoginMethods;
import com.studenttomsk.upYourParty.Views.Login.LoginPresenter;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
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

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterMethods.View, LoginMethods.View {
    EditText email, pass,  city, name,surname, otchestvo,numberPhone;
    TextView tologin;
    Button registerButton;
    ProgressDialog pd;
    Toast toast;
    RegisterPresenter presenter;
    LoginPresenter loginPresenter;
    String cityS, emailS, passS, confpassS, nameS,surnameS, otchestvoS,numberPhoneS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        else {
            getWindow().setStatusBarColor(Color.parseColor("#CFCFCF"));
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.arrow_color), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        email = findViewById(R.id.editTextEmail);
        pass = findViewById(R.id.Pass);
        toast = Toast.makeText(this,"Пароль имеет неверный формат",Toast.LENGTH_LONG);
        city = findViewById(R.id.edit_text_city);
        name = findViewById(R.id.text_name);
        surname = findViewById(R.id.text_surname);
        otchestvo = findViewById(R.id.text_otchestv);
        numberPhone = findViewById(R.id.text_phone);
        registerButton = findViewById(R.id.buttonRegist);
        registerButton.setOnClickListener(this);
        VKSdk.customInitialize(this,7511970,"5.52");
        presenter = new RegisterPresenter(this,new PostRegisterModel());
        loginPresenter = new LoginPresenter(this,new PostLoginModel());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //vkBtn.setClickable(true);

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



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonRegist: {
                PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
                EmailValidator emailValidator = new EmailValidator();
                FIOValidator fioValidator = new FIOValidator();
                PasswordValidator passwordValidator = new PasswordValidator();
                cityS = city.getText().toString();
                emailS = email.getText().toString();
                passS = pass.getText().toString();

                nameS = name.getText().toString();
                surnameS = surname.getText().toString();
                otchestvoS = otchestvo.getText().toString();
                numberPhoneS = numberPhone.getText().toString();

                if(!cityS.equals("")  && !emailS.equals("") && !passS.equals("")
                && !nameS.equals("") && !surnameS.equals("")
                && !otchestvoS.equals("") && !numberPhoneS.equals("")) {
                    if (emailValidator.validate(emailS)) {
                        if(phoneNumberValidator.validate(numberPhoneS)){
                        if (passS.length() >= 8) {
                            if(fioValidator.validate(name.getText().toString()) && fioValidator.validate(surname.getText().toString()) && fioValidator.validate(otchestvo.getText().toString())) {
                                if(fioValidator.validate(city.getText().toString())) {
                                    if(passwordValidator.validate(pass.getText().toString())) {
                                        registerButton.setClickable(false);
                                        RegistrationClass user = new RegistrationClass(
                                                email.getText().toString(), pass.getText().toString(), new ProfileClass(name.getText().toString()
                                                , surname.getText().toString(), otchestvo.getText().toString(), city.getText().toString(), numberPhone.getText().toString()));
                                        showPd();
                                        SingletonCity.getInstance().setCity(city.getText().toString());
                                        presenter.sendPostToRegisterUser(user);
                                    }
                                    else{
                                        toast.cancel();
                                        toast = Toast.makeText(this,"Пароль имеет неверный формат",Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                                else {
                                    toast.cancel();
                                    toast = Toast.makeText(this,"Поле город имеет неверный формат",Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                            else{
                                toast.cancel();
                                toast = Toast.makeText(this,"Поле Фамилия или Имя или Отчество имеет неверный формат",Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        }
                        else {
                            toast.cancel();
                            toast = Toast.makeText(this, "Пароль слишком короткий!", Toast.LENGTH_SHORT);
                            toast.show();

                        }

                        }
                        else{
                            toast.cancel();
                            toast = Toast.makeText(this, "Некорректный номер телефона", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    else{
                        toast.cancel();
                        toast = Toast.makeText(this, "Некорректный e-mail!", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
                else{
                    toast.cancel();
                    toast =  Toast.makeText(this,"Заполните все поля!",Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            }

        }
    }

    @Override
    public void toLoginActivity() {
        hidePd();
        Toast.makeText(this,"Вы успешно зарегистрированы ",Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void setToSharedPreferences(Map<String, String> map) {

    }

    @Override
    public void showPd() {
        pd = new ProgressDialog(this);
        pd.setTitle("Подождите");
        pd.setMessage("Регистрация...");
        pd.show();
    }

    @Override
    public void hidePd() {
        pd.dismiss();
    }

    @Override
    public void onResponseFailRule(String t) {
        hidePd();
        if(t.equals("409")){
            toast.cancel();
            toast = Toast.makeText(this,"Данный e-mail адресс уже используется",Toast.LENGTH_LONG);
            toast.show();
            registerButton.setClickable(true);
        }

    }
}
