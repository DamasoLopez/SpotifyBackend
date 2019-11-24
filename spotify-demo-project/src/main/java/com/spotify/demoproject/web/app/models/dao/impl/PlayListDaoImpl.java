package com.spotify.demoproject.web.app.models.dao.impl;
/*
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.demoproject.web.app.models.entity.PlayList;
import com.spotify.demoproject.web.app.models.entity.PlayListOut;*/

import java.io.IOException;

import java.util.HashMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spotify.demoproject.web.app.models.entity.PlayList;
import com.spotify.demoproject.web.app.models.services.IUserServices;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

@Service
public class PlayListDaoImpl {
	
	
	@Autowired
	IUserServices IUserService;
	   

	  public static void createPlayList(PlayList playList) throws SpotifyWebApiException {
		  SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setAccessToken(playList.getAccessToken())
		          .build();
		   CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(playList.getUserId(), playList.getPlayListName())
		          .build();
	    try {
	      final Playlist playlist = createPlaylistRequest.execute();

	     
	    } catch (IOException  e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	  }
	  
	  public Map<Integer,String> getAllPlaylist(String token,String refreshToken) {
		  SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setAccessToken(token)
		          .build();
		  GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
		          .getListOfCurrentUsersPlaylists()
		          .build();
		
		  Map<Integer,String> namesPlayList= new HashMap<>();
		  try {
		      final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();

		       PlaylistSimplified[] playlistSimplified= playlistSimplifiedPaging.getItems();
		       
		       int count=0;
		       for(PlaylistSimplified playlist:playlistSimplified) {
		    	   namesPlayList.put(count, playlist.getName());
		    	
		    	   count+=1;
		       }
		    } catch (IOException | SpotifyWebApiException e) {
		      System.out.println("Error: " + e.getMessage());
		    }
		  return namesPlayList;
		  }
	
	 
	  public Map<Integer,String> getTracks(String token,String refreshToken,String nameSong,String email) {
		 SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setAccessToken(token)
		          .build();
		 SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(nameSong)
				  .limit(10)
		          .build();
		 Map<Integer,String> songName= new HashMap<>();
	    try {
	       Paging<Track> trackPaging = searchTracksRequest.execute();
	       Track[] tracks=trackPaging.getItems();
	      
	       int count=0;
	       for(Track song:tracks) {
	    	   songName.put(count, song.getName());
	    	   count+=1;
	       }
	       IUserService.saveTrack(email, songName.get(0), nameSong);
	     
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	    return songName;
	  }
	 
	  
	/* 
	private final HttpClient httpClient = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_1_1)
	            .build();
	
	public Map<String,String> createPlayList(PlayList playList)throws Exception{
		
		String uri = "https://api.spotify.com/v1/users/"+playList.getUserId()+"/playlists";
		
		
	        Map<Object, Object> data = new HashMap<>();
	       data.put("name", playList.getPlayListName());
	       data.put("public", false);
	    
	        HttpRequest request = HttpRequest.newBuilder()
	                .POST(buildFormDataFromMap(data))
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

				// it works
	            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
	           
	            tokens.put("name", map.get("name"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	       
	    return tokens;
	}
	
	public Map<String,String> getAllPlaylist(String token, String refreshToken, String userid)throws Exception{
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
	        Map<String,String> tokens= new HashMap<String,String>();
	        
	        try {

	            // convert JSON string to Map
	            Map<String, String> map = mapper.readValue(json, Map.class);

				// it works
	            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
	           
	            tokens.put("userId", map.get("id"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	       
	    return tokens;
	}
	public Map<String,String> getTracks(String token, String refreshToken,String name)throws Exception{
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
	        Map<String,String> tokens= new HashMap<String,String>();
	        
	        try {

	            // convert JSON string to Map
	            Map<String, String> map = mapper.readValue(json, Map.class);

				// it works
	            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
	           
	            tokens.put("userId", map.get("id"));
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
	*/
	
}
