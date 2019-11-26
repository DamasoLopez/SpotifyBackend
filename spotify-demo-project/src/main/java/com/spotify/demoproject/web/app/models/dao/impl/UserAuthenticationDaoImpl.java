package com.spotify.demoproject.web.app.models.dao.impl;



import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.spotify.demoproject.web.app.models.entity.TokenAuthenticate;
import com.spotify.demoproject.web.app.models.entity.UrlAuthentication;



import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import java.util.HashMap;


@Service
public class UserAuthenticationDaoImpl {

	@Autowired
	UrlAuthentication urlAuthentication;
	
	@Autowired
	TokenAuthenticate tokenAuthenticate;
	
	 private final HttpClient httpClient = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_2)
	            .build();
	//Generate the Uri Authentication
	public String GenerateUriAuthentication(){
	    String uri = "https://accounts.spotify.com/authorize";
	    
	    
	    UriComponentsBuilder builder = UriComponentsBuilder
	    	    .fromUriString(uri)
	    	    // Add query parameter
	    	    .queryParam("client_id", urlAuthentication.getClientId())
	    	    .queryParam("redirect_uri", urlAuthentication.getRedirectUri())
	    	    .queryParam("response_type", urlAuthentication.getResponseType())
	    		.queryParam("scope", "playlist-modify-public playlist-read-private");
	    	String response = builder.toUriString();
	    	
	    	 
	    	 return response;
	}
	
	//Generate Token Access Authentication
	public Map<String,String> GenerateTokenAccesAuthentication(String code)throws Exception{
		String uri = "https://accounts.spotify.com/api/token";
		
		
		 
	        Map<Object, Object> data = new HashMap<>();
	        data.put("grant_type","authorization_code");
	        data.put("code",code);
	    	 data.put("redirect_uri",urlAuthentication.getRedirectUri());
	    	 data.put("client_id","afb8bc11aa234e918958092cefec78a6");
	    	 data.put("client_secret","fa2a3abc11be42e4a8b19284af6ac300");
	    	
	        HttpRequest request = HttpRequest.newBuilder()
	                .POST(buildFormDataFromMap(data))
	                .uri(URI.create(uri))
	                .header("Content-Type", "application/x-www-form-urlencoded")
	                .build();

	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	       
	        ObjectMapper mapper = new ObjectMapper();
	        String json = response.body();
	        Map<String,String> tokens= new HashMap<String,String>();
	        try {

	            // convert JSON string to Map
	            Map<String, String> map = mapper.readValue(json, Map.class);

	            tokenAuthenticate.setAccessToken(map.get("access_token"));
	            tokenAuthenticate.setRefreshToken(map.get("refresh_token"));
	           
	            tokens.put("refresh_token", map.get("refresh_token"));
	            tokens.put("access_token", map.get("access_token"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	       
	    return tokens;
	}
	private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
	 //Refresh the token Access
	public Map<String,String> RefreshTokenAccesAuthentication(String tokenRefresh)throws Exception{
		String uri = "https://accounts.spotify.com/api/token";
	        Map<Object, Object> data = new HashMap<>();
	        data.put("grant_type","refresh_token");
	        data.put("refresh_token",tokenRefresh);
	    	

	        HttpRequest request = HttpRequest.newBuilder()
	                .POST(buildFormDataFromMap(data))
	                .uri(URI.create(uri))
	                .header("Content-Type", "application/x-www-form-urlencoded")
	                .build();

	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	        ObjectMapper mapper = new ObjectMapper();
	        String json = response.body();
	        Map<String,String> tokens= new HashMap<String,String>();
	        try {

	            // convert JSON string to Map
	            Map<String, String> map = mapper.readValue(json, Map.class);

			
	            tokenAuthenticate.setAccessToken(map.get("access_token"));
	            tokenAuthenticate.setRefreshToken(map.get("refresh_token"));
	           
	            tokens.put("refresh_token", map.get("refresh_token"));
	            tokens.put("access_token", map.get("access_token"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	       
	    return tokens;
	        
	        
	    
	}
	
}
