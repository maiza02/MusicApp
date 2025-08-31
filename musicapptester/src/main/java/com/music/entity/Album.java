package com.music.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String albumname;

    private String yearpublished;

    private String coverimage;

    private String description;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;
    
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Song> songs;

    
	public Long getId() {
	        return id;
	    }

	public void setId(Long id) {
	        this.id = id;
	    }

	public String getAlbumname() {
		return albumname;
	}

	public void setAlbumname(String albumname) {
		this.albumname = albumname;
	}

	public String getYearpublished() {
		return yearpublished;
	}

	public void setYearpublished(String yearpublished) {
		this.yearpublished = yearpublished;
	}

	public String getCoverimage() {
		return coverimage;
	}

	public void setCoverimage(String coverimage) {
		this.coverimage = coverimage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Artist getArtist() {
	    return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	public List<Song> getSongs() {
	     return songs;
	}
	
	public void setSongs(List<Song> songs) {
	        this.songs = songs;
	}

}
