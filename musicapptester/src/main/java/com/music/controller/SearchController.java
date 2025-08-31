package com.music.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.entity.Album;
import com.music.entity.Artist;
import com.music.entity.Song;
import com.music.repository.AlbumRepository;
import com.music.repository.ArtistRepository;
import com.music.repository.SongRepository;

@Controller
@RequestMapping("/searching")
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    /**
     * Handles general search requests across albums, artists, and songs.
     *
     * @param query the search term entered by the user
     * @param model the model to pass search results to the view
     * @return the search results view
     */
    @GetMapping("/search")
    public String generalSearch(@RequestParam("query") String query, Model model) {
        logger.info("Search request received with query: {}", query);

        // Perform case-insensitive search in each repository
        List<Album> albums = albumRepository.findByAlbumnameContainingIgnoreCase(query);
        List<Artist> artists = artistRepository.findByArtistnameContainingIgnoreCase(query);
        List<Song> songs = songRepository.findBySongnameContainingIgnoreCase(query);

        logger.debug("Found {} albums, {} artists, and {} songs matching query '{}'",
                albums.size(), artists.size(), songs.size(), query);

        // Add results to model for rendering in the view
        model.addAttribute("albums", albums);
        model.addAttribute("artists", artists);
        model.addAttribute("songs", songs);
        model.addAttribute("query", query);

        return "search";
    }
}
