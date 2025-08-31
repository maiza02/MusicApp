package com.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.music.entity.Album;
import com.music.entity.Artist;
import com.music.repository.AlbumRepository;
import com.music.repository.ArtistRepository;
import com.music.repository.UserRepository;

@Controller
public class AdminController {
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private ArtistRepository artistRepository;

    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; 
    }
    
    @GetMapping("/album/table")
    public String adminAlbums(Model model) {
    	model.addAttribute("albums", albumRepository.findAll());
    	return "albumtable";
    }
    
    @GetMapping("/manage/users")
    public String adminUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users"; 
    }

    @GetMapping("/edit/{id}")
    public String showEditAlbumForm(@PathVariable Long id, Model model) {
        //logger.info("Entering showEditAlbumForm() with id={}", id);
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> {
                   // logger.error("Invalid album ID={}", id);
                    return new IllegalArgumentException("Invalid album ID");
                });

        model.addAttribute("album", album);
        model.addAttribute("artists", artistRepository.findAll());
        //logger.info("Exiting showEditAlbumForm() - album loaded");
        return "editalbums";
    }

    @PostMapping("/update/{id}")
    public String updateAlbum(@PathVariable Long id, @ModelAttribute Album albumDetails, @RequestParam Long artistId) {
       // logger.info("Entering updateAlbum() with id={}", id);

        Album album = albumRepository.findById(id)
                .orElseThrow(() -> {
                   // logger.error("Invalid album ID={}", id);
                    return new IllegalArgumentException("Invalid album ID");
                });

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> {
                   // logger.error("Invalid artist ID={}", artistId);
                    return new IllegalArgumentException("Invalid artist ID");
                });

        album.setAlbumname(albumDetails.getAlbumname());
        album.setDescription(albumDetails.getDescription());
        album.setCoverimage(albumDetails.getCoverimage());
        album.setYearpublished(albumDetails.getYearpublished());
        album.setArtist(artist);

        albumRepository.save(album);
        //logger.info("Album with id={} updated successfully", id);

       // logger.info("Exiting updateAlbum()");
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteAlbum(@PathVariable Long id) {
       // logger.info("Entering deleteAlbum() with id={}", id);

        if (!albumRepository.existsById(id)) {
          //  logger.warn("Attempted to delete non-existent album with id={}", id);
            return "redirect:/albums"; // Redirect without deleting
        }

        albumRepository.deleteById(id);
       // logger.info("Album with id={} deleted successfully", id);

        //logger.info("Exiting deleteAlbum()");
        return "redirect:/admin";
    }

}