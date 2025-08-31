package com.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homecontroller {

	@GetMapping("/")
    public String redirectToAlbums() {
        return "redirect:/albums";
    }
	
	@GetMapping("/add/song")
	public String addsongs() {
		return "addsong";
	}

}
