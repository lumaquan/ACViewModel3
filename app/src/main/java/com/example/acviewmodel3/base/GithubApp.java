package com.example.acviewmodel3.base;

import android.app.Application;
import android.content.Context;

public class GithubApp extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.create();
    }

    public static ApplicationComponent gitApplicationCompenent(Context context) {
        return ((GithubApp) context.getApplicationContext()).component;
    }
}
