package com.spotify.demoproject.web.app.authentication;

import java.io.IOException;


import org.springframework.stereotype.Component;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

@Component
public class IAutheticationClientCredentials {
	
	private static final String clientId="afb8bc11aa234e918958092cefec78a6";
	private static final String clientSecret="fa2a3abc11be42e4a8b19284af6ac300";

	  private SpotifyApi spotifyApi = new SpotifyApi.Builder()
	          .setClientId(clientId)
	          .setClientSecret(clientSecret)
	          .build();
	  private ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
	          .build();

	  public SpotifyApi clientCredentials() {
	    try {
	       ClientCredentials clientCredentials = clientCredentialsRequest.execute();

	      // Set access token for further "spotifyApi" object usage
	       spotifyApi.setAccessToken(clientCredentials.getAccessToken());
	       
	      System.out.println("Expires in: " + clientCredentials.getExpiresIn());
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
		return spotifyApi;
	  }

	  
}
