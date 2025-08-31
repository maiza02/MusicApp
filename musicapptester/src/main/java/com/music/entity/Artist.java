package com.music.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String artistname;

   // private String artistinfo;


    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Album> albums;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

    public String getArtistName() {
    	return artistname;
    }
    
    public void setArtistName(String artistname) {
    	this.artistname = artistname;
    }
    
  /*  public String getArtistInfo() {
    	return artistinfo;
    }
    
    public void setArtistInfo(String artistinfo) {
    	this.artistinfo = artistinfo;
    } */
    
    public List<Album> getAlbums() {
        return albums;
    }
    
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
