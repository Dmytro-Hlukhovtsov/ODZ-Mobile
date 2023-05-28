package com.example.odz.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.odz.db.dao.FilmDAO;
import com.example.odz.db.entities.Film;


@Database(entities = {Film.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FilmDAO filmDAO();
    public static AppDatabase db;

    public static AppDatabase getAppDatabase(Context context) {
        if(db == null) {
            db = Room
                    .databaseBuilder(context, AppDatabase.class, "films-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }
}
