package com.spotify.demoproject.web.app.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.spotify.demoproject.web.app.models.dao.impl.GetUserProfileDaoImpl;
import com.spotify.demoproject.web.app.models.dao.impl.UserAuthenticationDaoImpl;
import com.spotify.demoproject.web.app.models.entity.TokenAuthenticate;
import com.spotify.demoproject.web.app.models.services.IUserServices;

@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	IUserServices userServices;
	@Autowired
	UserAuthenticationDaoImpl userAuthenticationDaoImpl;
	@Autowired
	GetUserProfileDaoImpl getUserProfileDaoImpl;
	
	
	@RequestMapping(path="/login",produces="application/json")
	public  Map<String,String> login(Model model) {
		Map<String,String> response= new HashMap<String,String>();
		
		response.put("url",userAuthenticationDaoImpl.GenerateUriAuthentication());
		return response;
	}
	
	@GetMapping(path="/loginSuccesfull/{code}",produces="application/json")
	public  Map<String,String> loginSuccesfull(@PathVariable String code) throws Exception {
		
		
		
		
		return userAuthenticationDaoImpl.GenerateTokkenAccesAuthentication(code);
	}
	@GetMapping(path="/getUserId/{token}/{refreshToken}",produces="application/json")
	public  Map<String,String> getUserId(@PathVariable String token,@PathVariable String refreshToken) throws Exception {
		
		
		
		
		return getUserProfileDaoImpl.getUser(refreshToken, token);
	}
	
	
	
}
