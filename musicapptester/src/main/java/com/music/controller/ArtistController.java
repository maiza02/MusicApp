package com.music.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.music.entity.Artist;
import com.music.repository.ArtistRepository;

@Controller
@RequestMapping("/artist")
public class ArtistController {

    private static final Logger logger = LoggerFactory.getLogger(ArtistController.class);

    @Autowired
    private ArtistRepository artistRepository;

    /**
     * Handles AJAX search requests for artists by name.
     * Returns a JSON response with matching artists.
     *
     * @param term the search keyword (partial or full artist name)
     * @return a list of artist objects in key-value map form (id + name)
     */
    @GetMapping("/search")
    @ResponseBody
    public List<Map<String, Object>> searchArtists(@RequestParam String term) {
        logger.info("Searching artists with term: {}", term);

        // Query repository for artists whose names contain the search term (case-insensitive)
        List<Artist> artists = artistRepository.findByArtistnameContainingIgnoreCase(term);

        logger.debug("Found {} artists matching '{}'", artists.size(), term);

        // Convert list of Artist objects into list of maps for JSON response
        return artists.stream().map(artist -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", artist.getId());
            map.put("name", artist.getArtistName());
            return map;
        }).collect(Collectors.toList());
    }
}
