package com.music.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String songname;
	
	private String duration;
	
	private String songURL;
	
	@ManyToOne
	@JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;
	
	 @ManyToOne
	 @JoinColumn(name = "album_id", nullable = false)
	 private Album album;
	
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
