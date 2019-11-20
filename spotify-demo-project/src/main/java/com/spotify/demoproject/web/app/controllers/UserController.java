package com.spotify.demoproject.web.app.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.spotify.demoproject.web.app.authentication.UserAunthentication;
import com.spotify.demoproject.web.app.authentication.UserAuthenticationDaoImpl;
import com.spotify.demoproject.web.app.models.entity.TokenAuthenticate;
import com.spotify.demoproject.web.app.models.services.IUserServices;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserAunthentication userAuthentication;
	
	@Autowired
	IUserServices userServices;
	@Autowired
	UserAuthenticationDaoImpl userAuthenticationDaoImpl;
	
	
	
	@RequestMapping(path="/login",produces="application/json")
	public  Map<String,String> login(Model model) {
		Map<String,String> response= new HashMap<String,String>();
		
		response.put("url",userAuthenticationDaoImpl.GenerateUriAuthentication());
		return response;
	}
	
	@GetMapping(path="/loginSuccesfull/{code}",consumes="application/json",produces="application/json")
	public  TokenAuthenticate loginSuccesfull(@PathVariable String code) throws Exception {
		System.out.println("holaaa");
		userAuthenticationDaoImpl.GenerateTokkenAccesAuthentication(code);
		
		return null;
	}
	
	
	
}
