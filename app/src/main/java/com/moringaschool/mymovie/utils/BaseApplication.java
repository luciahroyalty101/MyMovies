package com.moringaschool.mymovie.utils;

import android.app.Application;

import static com.moringaschool.mymovie.utils.ModeStorage.getMode;


public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        getMode(this);
    }
}
