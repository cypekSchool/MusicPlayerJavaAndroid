package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoPlayer {
    @Insert
    public void insertArtist(Artist artist);

    @Insert
    public void insertSong(Song song);

    @Delete
    public void deleteArtist(Artist artist);

    @Delete
    public void deleteSong(Song song);

    @Update
    public void editArtist(Artist artist);

    @Update
    public void editSong(Song song);

    @Query("SELECT * FROM artists")
    public List<Artist> getAllArtists();

    @Query("SELECT * FROM songs")
    public List<Song> getAllSongs();

//    @Query("SELECT * FROM artists WHERE name LIKE :namePattern")
//    public List<Song> getArtist(String namePattern);
}
