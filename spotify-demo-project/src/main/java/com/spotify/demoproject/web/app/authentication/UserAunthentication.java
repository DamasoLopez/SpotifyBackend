package com.spotify.demoproject.web.app.authentication;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;


import org.springframework.stereotype.Component;
@Component
public class UserAunthentication {
	
	  private static final String clientId="afb8bc11aa234e918958092cefec78a6";
	  private static final String clientSecret="fa2a3abc11be42e4a8b19284af6ac300";
	  private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:4200/");

	  private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
	          .setClientId(clientId)
	          .setClientSecret(clientSecret)
	          .setRedirectUri(redirectUri)
	          .build();
	  private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
	          .build();

	  public  URI authorizationCodeUri_Sync() {
	    final URI uri = authorizationCodeUriRequest.execute();
	    
	    System.out.println("URI: " + uri.toString());
	    return uri;
	  }
}