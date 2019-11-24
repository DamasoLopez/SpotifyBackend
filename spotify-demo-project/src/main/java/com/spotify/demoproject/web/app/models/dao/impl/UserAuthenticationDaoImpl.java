package com.spotify.demoproject.web.app.models.dao.impl;



import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spotify.demoproject.web.app.models.entity.TokenAuthenticate;
import com.spotify.demoproject.web.app.models.entity.UrlAuthentication;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAuthenticationDaoImpl {

	@Autowired
	UrlAuthentication urlAuthentication;
	
	@Autowired
	TokenAuthenticate tokenAuthenticate;
	
	 private final HttpClient httpClient = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_2)
	            .build();
	
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
	
	
	public Map<String,String> GenerateTokkenAccesAuthentication(String code)throws Exception{
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

				// it works
	            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});

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
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
	 

	  public Map<String,String> authorizationCodeRefresh_Sync(String refreshToken) throws SpotifyWebApiException {
		  SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setClientId("afb8bc11aa234e918958092cefec78a6")
		          .setClientSecret("fa2a3abc11be42e4a8b19284af6ac300")
		          .setRefreshToken(refreshToken)
		          .build();
		 AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
		          .build();
		 Map<String,String> tokens=new HashMap<>();
		  try {
	      AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

	      
	      tokens.put("accestokens",authorizationCodeCredentials.getAccessToken());
	      tokens.put("refreshtoken", authorizationCodeCredentials.getRefreshToken());

	      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
	    } catch (IOException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
		  return tokens;
	  }
	/*public String RefreshTokkenAccesAuthentication(String tokkenRefresh)throws Exception{
		String uri = "https://accounts.spotify.com/api/token";
		TokenAuthenticate tokenAuthenticate=new TokenAuthenticate();
	        Map<Object, Object> data = new HashMap<>();
	        data.put("grant_type","refresh_token");
	        data.put("refresh_token",tokkenRefresh);
	    	

	        HttpRequest request = HttpRequest.newBuilder()
	                .POST(buildFormDataFromMap(data))
	                .uri(URI.create(uri))
	                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
	                .header("Content-Type", "application/x-www-form-urlencoded")
	                .build();

	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	       
	        
	        
	    return response.body();
	}*/
	
}
