package com.studenttomsk.upYourParty;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;



public class ThisApplication extends Application {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(base);
    }
}