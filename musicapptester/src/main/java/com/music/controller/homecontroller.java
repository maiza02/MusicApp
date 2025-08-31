package com.music.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homecontroller {

    private static final Logger logger = LoggerFactory.getLogger(homecontroller.class);

    /**
     * Redirects the root URL ("/") to the albums page.
     *
     * @return redirect to albums
     */
    @GetMapping("/")
    public String redirectToAlbums() {
        logger.debug("Redirecting from root (/) to /albums");
        return "redirect:/albums";
    }

    /**
     * Displays the Add Song page.
     *
     * @return the addsong view name
     */
    @GetMapping("/add/song")
    public String addSongs() {
        logger.info("Accessed Add Song page");
        return "addsong";
    }
}
