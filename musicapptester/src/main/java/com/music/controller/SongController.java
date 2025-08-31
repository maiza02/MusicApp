package com.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.entity.Album;
import com.music.entity.Artist;
import com.music.entity.Song;
import com.music.repository.AlbumRepository;
import com.music.repository.ArtistRepository;
import com.music.repository.SongRepository;

@Controller
public class SongController {

	@Autowired
	private AlbumRepository albumRepository;
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private SongRepository songRepository;
	

	
	@PostMapping("/add/song")
	public String addSong(@RequestParam String songname,
	                      @RequestParam String duration,
	                      @RequestParam String songURL,
	                      @RequestParam String albumname,
	                      @RequestParam String artistName) {

	    // Get or create artist
	    Artist artist = artistRepository.findByArtistnameIgnoreCase(artistName);
	    if (artist == null) {
	        artist = new Artist();
	        artist.setArtistName(artistName);
	        artistRepository.save(artist);
	    }

	    // Get or create album (by name and artist)
	    Album album = albumRepository.findByAlbumnameIgnoreCaseAndArtist(albumname, artist);
	    if (album == null) {
	        album = new Album();
	        album.setAlbumname(albumname);
	        album.setArtist(artist);
	        album.setYearpublished("Unknown");
	        album.setCoverimage("default.jpg");
	        album.setDescription("No description.");
	        albumRepository.save(album);
	    }

	    //Check if the song already exists for this album
	    Song existing = songRepository.findBySongnameIgnoreCaseAndAlbum(songname, album);
	    if (existing != null) {
	        // Optionally redirect with error message
	        return "redirect:/add/song?error=exists";
	    }

	    //Create and save song if it doesn't exist
	    Song song = new Song();
	    song.setSongname(songname);
	    song.setDuration(duration);
	    song.setSongURL(songURL);
	    song.setAlbum(album);
	    song.setArtist(artist);

	    songRepository.save(song);

	    return "redirect:/admin?songAdded=true";
	}

}
