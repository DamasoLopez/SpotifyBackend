package com.spotify.demoproject.web.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spotify.demoproject.web.app.models.dao.impl.PlayListDaoImpl;

import com.spotify.demoproject.web.app.models.entity.PlayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/playlist")
public class PlayListController {

	@Autowired
	PlayListDaoImpl playListDaoImpl;

	@RequestMapping(value = "/createPlayList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity createPlayList(@Valid @RequestBody PlayList playList) throws Exception {
		playListDaoImpl.createPlayList(playList);
		return null;
	}

	@GetMapping(path = "/getAllPlaylist/{token}/{refreshToken}/{userId}")
	public Map<Integer, String> getAllPlaylist(@PathVariable String token, @PathVariable String refreshToken,
			@PathVariable String userId) throws Exception {

		return playListDaoImpl.getAllPlaylist(token, refreshToken, userId);

	}

	@GetMapping(path = "/searchTrack/{token}/{refreshToken}/{nameSong}/{email}")
	public Map<Integer, String> searchTrack(@PathVariable String token, @PathVariable String refreshToken,
			@PathVariable String nameSong, @PathVariable String email) throws Exception {

		return playListDaoImpl.getTracks(token, refreshToken, nameSong, email);

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}

}
