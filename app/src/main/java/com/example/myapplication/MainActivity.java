package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
    Button newSongBtn;

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

        newSongBtn = findViewById(R.id.button);
        newSongBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.add_song_dialog);
//                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                    }
                }
        );

        songsLV = findViewById(R.id.songs);
        songsArray = new ArrayList<>();
        songsAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                songsArray
        );

        songsLV.setAdapter(songsAdapter);

        songsLV.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Song song = songsArray.get(i);
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        playerDatabase.getDaoPlayer().deleteSong(song);
                                        songsArray.clear();
                                        songsArray.addAll(playerDatabase.getDaoPlayer().getAllSongs());
//                                        songsAdapter.notifyDataSetChanged();
                                    }
                                }
                        );
                        return false;
                    }
                }
        );


//        addArtistToDatabase("Kult", "Kultowy zespoół");
//
//
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        Artist artist = playerDatabase.getDaoPlayer().getAllArtists().get(0);
//
//                        addSongsToDatabase("Po co wolność", artist);
//                        addSongsToDatabase("Baranek", artist);
//                        addSongsToDatabase("Gdy nie ma dzieci", artist);
//                    }
//                }
//        );

        displaySongs();

    }


    private void displaySongs() {
        songsArray.clear();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(
                new Runnable() {
                    @Override
                    public void run() {

                        songsArray.addAll(playerDatabase.getDaoPlayer().getAllSongs());

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