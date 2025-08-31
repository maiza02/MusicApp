package com.music.entity;

import jakarta.persistence.*;

/**
 * Song entity representing the "songs" table in the database.
 * Each song belongs to an album and an artist.
 */
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    private String songname; // Name of the song

    private String duration; // Duration of the song, e.g., "3:45"

    private String songURL; // URL or path to the song file

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist; // The artist who performed the song

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album; // The album this song belongs to

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSongURL() {
        return songURL;
    }

    public void setSongURL(String songURL) {
        this.songURL = songURL;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
