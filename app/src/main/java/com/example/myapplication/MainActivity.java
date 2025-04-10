package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ListView songsLV;
    ArrayAdapter<Song> songsAdapter;

    PlayerDatabase playerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }

            @Override
            public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                super.onDestructiveMigration(db);
            }
        };

        playerDatabase = Room.databaseBuilder(
                MainActivity.this,
                PlayerDatabase.class,
                "artists"
        ).addCallback(dbCallback).allowMainThreadQueries().build();



        songsLV = findViewById(R.id.songs);
        songsLV.setAdapter(songsAdapter);

        addArtistToDatabase("Kult", "Kultowy zespoół");
    }

    private void addArtistToDatabase(String name, String description) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        Handler handler = new Handler();
        executorService.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        playerDatabase.getDaoPlayer().insertArtist(new Artist(name, description));
//                        handler.post((
//                                new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                        ));
                    }
                }
        );
    }

}