package com.spotify.demoproject.web.app.models.dao.impl;


import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.demoproject.web.app.models.entity.PlayList;
import com.spotify.demoproject.web.app.models.services.IUserServices;


import java.net.URI;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.util.UriComponentsBuilder;




import java.net.http.HttpClient;

import java.net.http.HttpResponse;

@Service
public class PlayListDaoImpl {
	
	
	@Autowired
	IUserServices iUserService;

	
	private final HttpClient httpClient = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_1_1)
	            .build();
	
	public void createPlayList(PlayList playList)throws Exception{
		
		String uri = "https://api.spotify.com/v1/users/"+playList.getUserId()+"/playlists";
		
		
	        Map<Object, Object> data = new HashMap<>();
	       data.put("name", playList.getPlayListName());
	       
	       String jsonout = new ObjectMapper().writeValueAsString(data);
	       
	        HttpRequest request = HttpRequest.newBuilder()
	                .POST(HttpRequest.BodyPublishers.ofString(jsonout))
	                .uri(URI.create(uri))
	                .header("Authorization", "Bearer "+playList.getAccessToken())
	                .header("Content-Type", "application/json")
	                
	                .build();
	  
	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	        ObjectMapper mapper = new ObjectMapper();
	        String json = response.body();
	        Map<String,String> tokens= new HashMap<String,String>();
	        try {

	            // convert JSON string to Map
	            Map<String, String> map = mapper.readValue(json, Map.class);

				
	            tokens.put("name", map.get("name"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	       
	    
	}
	
	public Map<Integer,String> getAllPlaylist(String token, String refreshToken, String userid)throws Exception{
		String uri = "https://api.spotify.com/v1/users/"+userid+"/playlists";
		
	        HttpRequest request = HttpRequest.newBuilder()
	                .GET()
	                .uri(URI.create(uri))
	                .header("Authorization", "Bearer "+token)
	                .header("Content-Type", "application/json")
	                .build();

	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	        ObjectMapper mapper = new ObjectMapper();
	        String json = response.body();
	        Map<Integer,String> tokens= new HashMap<Integer,String>();
	        Map<String,String> playlist=new HashMap<String,String>();
	        List<Map<String,String>> items;
	        try {

	            // convert JSON string to Map
	            Map<String, List<Map<String,String>>> map = mapper.readValue(json, Map.class);
	            items=map.get("items");
	            for(int i=0;items.size()>i;i++) {
	            	playlist=items.get(i);
	            	tokens.put(i, playlist.get("name"));
	            }
			
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	       
	    return tokens;
	}
	public Map<Integer,String> getTracks(String token, String refreshToken,String name,String email)throws Exception{
		String uri = "https://api.spotify.com/v1/search";
		
		   Map<Object, Object> data = new HashMap<>();
	       data.put("q", name);
	       data.put("type", "track");
	    
	      
		    UriComponentsBuilder builder = UriComponentsBuilder
		    	    .fromUriString(uri)
		    	    // Add query parameter
		    	    .queryParam("q", name)
		    		.queryParam("type","track")
		    		.queryParam("limit","10");

	        HttpRequest request = HttpRequest.newBuilder()
	                .GET()
	                .uri(URI.create(builder.toUriString()))
	                .header("Authorization", "Bearer "+token)
	                .header("Content-Type", "application/json")
	                .build();

	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	        ObjectMapper mapper = new ObjectMapper();
	        String json = response.body();
	        Map<Integer,String> mapOfTracks= new HashMap<Integer,String>();
	        Map<String, List<Map<String, String>>> tracks;
	        List<Map<String, String>> items;
	        Map<String, String> maps;
	        try {

	            // convert JSON string to Map
	        	Map<String,Map<String,List<Map<String,String>>>>  map = mapper.readValue(json, Map.class);

				
	        	tracks=map.get("tracks");
	            items=tracks.get("items");
	            for(int i=0;items.size()>i;i++) {
	            	maps=items.get(i);
	            	mapOfTracks.put(i, maps.get("name"));
	            }
	          
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        iUserService.saveTrack(email, mapOfTracks.get(0), name);
	    return mapOfTracks;
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
	
	
}
