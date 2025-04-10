package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Artist.class, Song.class}, version = 1, exportSchema = false)
public abstract class PlayerDatabase extends RoomDatabase {
    public abstract DaoPlayer getDaoPlayer();
}
