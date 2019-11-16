package com.spotify.demoproject.web.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;


import com.spotify.demoproject.web.app.authentication.IAutheticationClientCredentials;
import com.spotify.demoproject.web.app.search.SearchTrack;
import com.wrapper.spotify.SpotifyApi;

@Controller
public class PlayListController {
	
	@Autowired
	IAutheticationClientCredentials iAutheticationClientCredentials;
	
	@Autowired
	SearchTrack searchTrack;
	
	@GetMapping("/searchsong")
	public String SearchSong(@RequestParam(name="namesong") String namesong, Model model) {
		SpotifyApi spotifyApi=iAutheticationClientCredentials.clientCredentials();
		List<String> listSong=searchTrack.searchTracks(spotifyApi, namesong);
		model.addAttribute("songlist", listSong);
		return "index";
	}
	
	
	
}
