package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ListView songsLV;
    ArrayAdapter<Song> songsAdapter;
    ArrayList<Song> songsArray;
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
        songsArray = new ArrayList<>();
       /* songsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                songsArray
        );

        songsLV.setAdapter(songsAdapter);*/

        displaySongs();

//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        Artist artist = playerDatabase.getDaoPlayer().getAllArtists().get(0);
//
//                        addSongsToDatabase("Ryby", artist);
//                    }
//                }
//        );


//        addArtistToDatabase("Kult", "Kultowy zespoół");
    }

    private void displaySongs() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        List<Song> wszystkie = playerDatabase.getDaoPlayer().getAllSongs();
                        for (Song song : wszystkie) {
                            songsArray.add(song);
                        }
                       songsAdapter = new ArrayAdapter<>(
                                MainActivity.this,
                                android.R.layout.simple_list_item_1,
                               wszystkie
                        );

                        songsLV.setAdapter(songsAdapter);
                        songsAdapter.notifyDataSetChanged();

                    }
                }
        );
    }

    private void addSongsToDatabase(String title, Artist artist) {
        addSongsToDatabase(title, artist, 0);
    }
    private void addSongsToDatabase(String title, Artist artist, int plays) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        playerDatabase.getDaoPlayer().insertSong(new Song(title, artist.getId(), plays));
                    }
                }
        );
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