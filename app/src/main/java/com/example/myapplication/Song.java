package com.example.myapplication;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;



@Entity(
        tableName = "songs",
        foreignKeys = {
                @ForeignKey(
                        entity = Artist.class,
                        parentColumns = "id",
                        childColumns = "artistId",
                        onDelete = CASCADE,
                        onUpdate = CASCADE
                )
        }
)
public class Song {
    @Override
    public String toString() {
        return title + " - " + plays + " odtworzeń";
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int artistId;
    private int plays;

    public Song(String title, int artistId, int plays) {
        this.id = 0;
        this.title = title;
        this.artistId = artistId;
        this.plays = plays;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }
}
