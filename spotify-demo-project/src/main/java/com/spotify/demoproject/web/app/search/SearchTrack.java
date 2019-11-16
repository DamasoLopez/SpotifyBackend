package com.spotify.demoproject.web.app.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

@Component
public class SearchTrack {
	  //Search 10 Songs
	  public List<String> searchTracks(SpotifyApi spotifyApi,String nameSong) {
		  List<String> listSongs= new ArrayList<>();
		  
	    try {
	    	
	      SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(nameSong)	          
	    		  .limit(10)
			      .build();
	      Paging<Track> trackPaging = searchTracksRequest.execute();
	      
	      Track[] tracks=trackPaging.getItems();
	      for(Track track : tracks) {
	    	  listSongs.add(track.getName());
	      }
	      
	      System.out.println("Total: " + trackPaging.getTotal());
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	    return listSongs;
	  }

}
