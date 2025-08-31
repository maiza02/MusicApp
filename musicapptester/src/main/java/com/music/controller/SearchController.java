package com.music.controller;

import java.util.List;


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

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/search")
    public String generalSearch(@RequestParam("query") String query, Model model) {
        List<Album> albums = albumRepository.findByAlbumnameContainingIgnoreCase(query);
        List<Artist> artists = artistRepository.findByArtistnameContainingIgnoreCase(query);
        List<Song> songs = songRepository.findBySongnameContainingIgnoreCase(query);

        model.addAttribute("albums", albums);
        model.addAttribute("artists", artists);
        model.addAttribute("songs", songs);
        model.addAttribute("query", query);

        return "search";
    }
}

