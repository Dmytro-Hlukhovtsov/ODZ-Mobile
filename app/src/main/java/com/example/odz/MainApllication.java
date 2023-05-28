package com.example.odz;

import android.app.Application;

import com.example.odz.db.AppDatabase;

public class MainApllication extends Application {
    public static MainApllication app;
    private AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        db = AppDatabase.getAppDatabase(getApplicationContext());
    }

    public static MainApllication getInstance() {
        return app;
    }

    public AppDatabase getDatabase() {
        return db;
    }


}
