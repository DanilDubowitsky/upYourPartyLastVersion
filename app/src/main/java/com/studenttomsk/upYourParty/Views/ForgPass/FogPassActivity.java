package com.studenttomsk.upYourParty.Views.ForgPass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.studenttomsk.upYourParty.Classes.EmailValidator;
import com.studenttomsk.upYourParty.Classes.SingletonForgPassEmail;
import com.studenttomsk.upYourParty.Network.ForgPassModel;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Classes.ClassEmail;

public class FogPassActivity extends AppCompatActivity implements ForgPassMethods.ForgPassView, View.OnClickListener {
    EditText forgPass;
    Button sendPass;
    Toast toast;
    ForgPassPresenter forgPassPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fog_pass);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        else {
            getWindow().setStatusBarColor(Color.parseColor("#CFCFCF"));
        }
        toast = Toast.makeText(this, "Вы не подтвердили почту!", Toast.LENGTH_LONG);
        sendPass = findViewById(R.id.sendPass);
        sendPass.setOnClickListener(this);
        forgPass = findViewById(R.id.forgot_pass_email);
        forgPass.setText(SingletonForgPassEmail.getInstance().getEmail());
        forgPassPresenter = new ForgPassPresenter(this,new ForgPassModel());

    }

    @Override
    public void onSuccessReset() {
        sendPass.setClickable(true);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_message,null);
        mBuilder.setView(view);
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onFailrule(int code) {
        if(code == 409) {
            toast.cancel();
            toast = Toast.makeText(this, "Вы не подтвердили почту!", Toast.LENGTH_SHORT);
            toast.show();
            sendPass.setClickable(true);
        }
        else {
            toast.cancel();
            toast = Toast.makeText(this, "Данный E-mail незарегистрирован", Toast.LENGTH_SHORT);
            toast.show();
            sendPass.setClickable(true);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.sendPass){
            EmailValidator emailValidator = new EmailValidator();
            if(!forgPass.getText().toString().equals("")){
                if(emailValidator.validate(forgPass.getText().toString())) {
                    sendPass.setClickable(false);
                    forgPassPresenter.sendFogPass(new ClassEmail(forgPass.getText().toString()));
                }
                else{
                    toast.cancel();
                    toast = Toast.makeText(this, "E-mail имеет неверный формат", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
            else{
                toast.cancel();
                toast = Toast.makeText(this, "Введите E-mail", Toast.LENGTH_SHORT);
                toast.show();
                sendPass.setClickable(true);
            }
        }
    }
}
