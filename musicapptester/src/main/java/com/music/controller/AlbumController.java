package com.music.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.music.entity.Album;
import com.music.entity.Artist;
import com.music.entity.Song;
import com.music.repository.AlbumRepository;
import com.music.repository.ArtistRepository;
import com.music.repository.SongRepository;

/**
 * AlbumController
 * 
 * Handles all operations related to albums such as:
 * - Viewing all albums
 * - Adding a new album
 * - Viewing details of a specific album
 */
@Controller
@RequestMapping("/albums")
public class AlbumController {

    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    /**
     * Displays a list of all albums.
     */
    @GetMapping
    public String listAlbums(Model model) {
        logger.info("Fetching all albums.");
        model.addAttribute("albums", albumRepository.findAll());
        return "albums";
    }

    /**
     * Displays form to add a new album.
     */
    @GetMapping("/add/album")
    public String showAddAlbumForm(Model model) {
        logger.info("Loading form to add new album.");
        model.addAttribute("album", new Album());
        model.addAttribute("artists", artistRepository.findAll());
        return "addalbum";
    }

    /**
     * Handles submission of new album form.
     */
    @PostMapping("/add")
    public String addAlbum(@RequestParam String albumname,
                           @RequestParam String yearpublished,
                           @RequestParam String coverimage,
                           @RequestParam String description,
                           @RequestParam String artistName) {

        logger.info("Attempting to add album '{}', by artist '{}'.", albumname, artistName);

        // Check if artist exists
        Artist artist = artistRepository.findByArtistnameIgnoreCase(artistName);

        // If artist doesn't exist, create new one
        if (artist == null) {
            logger.warn("Artist '{}' not found. Creating new artist.", artistName);
            artist = new Artist();
            artist.setArtistName(artistName);
            artistRepository.save(artist);
            logger.info("New artist '{}' saved to database.", artistName);
        }

        Album album = new Album();
        album.setAlbumname(albumname);
        album.setYearpublished(yearpublished);
        album.setCoverimage(coverimage);
        album.setDescription(description);
        album.setArtist(artist);

        albumRepository.save(album);
        logger.info("Album '{}' saved successfully.", albumname);

        return "redirect:/admin?albumAdded=true";
    }

    /**
     * Displays details of a specific album, including its songs.
     */
    @GetMapping("/album/{id}")
    public String viewAlbumDetails(@PathVariable Long id, Model model) {
        logger.info("Fetching details for album with id={}", id);

        Optional<Album> optionalAlbum = albumRepository.findById(id);

        if (optionalAlbum.isPresent()) {
            Album album = optionalAlbum.get();
            logger.info("Album '{}' found. Fetching songs...", album.getAlbumname());
            
            List<Song> songs = songRepository.findByAlbum(album);
            model.addAttribute("album", album);
            model.addAttribute("songs", songs);

            logger.info("Album details loaded successfully for id={}", id);
            return "albumdetails";
        } else {
            logger.warn("Album with id={} not found. Redirecting to albums list.", id);
            return "redirect:/albums";
        }
    }
}
