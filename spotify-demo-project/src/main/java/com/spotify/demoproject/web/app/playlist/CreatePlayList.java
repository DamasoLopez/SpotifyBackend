package com.spotify.demoproject.web.app.playlist;
	import com.wrapper.spotify.SpotifyApi;
	import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.requests.data.playlists.AddTracksToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
	
	import java.io.IOException;
	import java.util.concurrent.CancellationException;
	import java.util.concurrent.CompletableFuture;
	import java.util.concurrent.CompletionException;
public class CreatePlayList {
	
	  public  Playlist createPlaylist_Sync(String accessToken,String userId,String name) {
		  SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setAccessToken(accessToken)
		          .build();
		  CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(userId, name)
		          .build();
		  Playlist playlist = null;
		  try {
		  playlist = createPlaylistRequest.execute();
		 
			  System.out.println("Name: " + playlist.getName());
	    } catch (IOException | SpotifyWebApiException e) {
	    	  System.out.println("Error: " + e.getMessage());
	    }
		  return playlist;
	  }
	  
	  

	  public static void addTracksToPlaylist_Sync(String accessToken,String[] uris,String playlistId) {
		  
		  uris = new String[]{"spotify:track:01iyCAUm8EvOFqVWYJ3dVX"};

		  SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setAccessToken(accessToken)
		          .build();
		  AddTracksToPlaylistRequest addTracksToPlaylistRequest = spotifyApi
		          .addTracksToPlaylist(playlistId, uris)
		          .build();
		  
	    try {
	      SnapshotResult snapshotResult = addTracksToPlaylistRequest.execute();

	      System.out.println("Snapshot ID: " + snapshotResult.getSnapshotId());
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	  }
	  
	
}
