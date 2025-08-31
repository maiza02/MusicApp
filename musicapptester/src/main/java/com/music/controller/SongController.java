package com.music.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
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

/**
 * SongController
 * 
 * Handles operations related to songs, such as adding new songs.
 */
@Controller
public class SongController {

    private static final Logger logger = LoggerFactory.getLogger(SongController.class);

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    /**
     * Adds a new song to the database. Automatically creates the artist or album
     * if they do not exist.
     *
     * @param songname   the name of the song
     * @param duration   duration of the song
     * @param songURL    URL of the song
     * @param albumname  the album name
     * @param artistName the artist name
     * @return redirect URL after adding the song
     */
    @PostMapping("/add/song")
    public String addSong(@RequestParam String songname,
                          @RequestParam String duration,
                          @RequestParam String songURL,
                          @RequestParam String albumname,
                          @RequestParam String artistName) {

        logger.info("Adding song '{}' by artist '{}', album '{}'", songname, artistName, albumname);

        // Get or create artist
        Artist artist = artistRepository.findByArtistnameIgnoreCase(artistName);
        if (artist == null) {
            logger.warn("Artist '{}' not found. Creating new artist.", artistName);
            artist = new Artist();
            artist.setArtistName(artistName);
            artistRepository.save(artist);
            logger.info("Artist '{}' created.", artistName);
        }

        // Get or create album
        Album album = albumRepository.findByAlbumnameIgnoreCaseAndArtist(albumname, artist);
        if (album == null) {
            logger.warn("Album '{}' not found for artist '{}'. Creating new album.", albumname, artistName);
            album = new Album();
            album.setAlbumname(albumname);
            album.setArtist(artist);
            album.setYearpublished("Unknown");
            album.setCoverimage("default.jpg");
            album.setDescription("No description.");
            albumRepository.save(album);
            logger.info("Album '{}' created for artist '{}'.", albumname, artistName);
        }

        // Check if the song already exists
        Song existing = songRepository.findBySongnameIgnoreCaseAndAlbum(songname, album);
        if (existing != null) {
            logger.warn("Song '{}' already exists in album '{}'. Skipping add.", songname, albumname);
            return "redirect:/add/song?error=exists";
        }

        // Create and save new song
        Song song = new Song();
        song.setSongname(songname);
        song.setDuration(duration);
        song.setSongURL(songURL);
        song.setAlbum(album);
        song.setArtist(artist);

        songRepository.save(song);
        logger.info("Song '{}' added successfully to album '{}' by artist '{}'.", songname, albumname, artistName);

        return "redirect:/admin?songAdded=true";
    }
}
