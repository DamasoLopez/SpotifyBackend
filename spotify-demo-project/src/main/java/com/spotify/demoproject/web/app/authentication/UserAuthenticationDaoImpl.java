package com.spotify.demoproject.web.app.authentication;



import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Component;

import org.springframework.web.util.UriComponentsBuilder;


import com.spotify.demoproject.web.app.models.entity.TokenAuthenticate;
import com.spotify.demoproject.web.app.models.entity.UrlAuthentication;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
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
	    	    .queryParam("response_type", urlAuthentication.getResponseType());
	    
	    	String response = builder.toUriString();
	    	 System.out.println(response);
	    	 return response;
	}
	
	
	public TokenAuthenticate GenerateTokkenAccesAuthentication(String code)throws Exception{
		String uri = "https://accounts.spotify.com/api/token";
		TokenAuthenticate tokenAuthenticate=new TokenAuthenticate();
	        Map<Object, Object> data = new HashMap<>();
	        data.put("grant_type",urlAuthentication.getGrant_type());
	        data.put("code",code);
	    	 data.put("redirect_uri",urlAuthentication.getRedirectUri());
	    	 data.put("client_id","afb8bc11aa234e918958092cefec78a6");
	    	 data.put("client_secret","fa2a3abc11be42e4a8b19284af6ac300");

	        HttpRequest request = HttpRequest.newBuilder()
	                .POST(buildFormDataFromMap(data))
	                .uri(URI.create(uri))
	                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
	                .header("Content-Type", "application/x-www-form-urlencoded")
	                .build();

	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	        // print status code
	        System.out.println(response.statusCode());

	        // print response body
	        System.out.println(response.body());
	        tokenAuthenticate.setAccessToken(response.body());
	        tokenAuthenticate.setRefreshToken(response.body());
	    return tokenAuthenticate;
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
	public String RefreshTokkenAccesAuthentication(String tokkenRefresh)throws Exception{
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

	        // print status code
	        System.out.println(response.statusCode());

	        // print response body
	        System.out.println(response.body());
	        
	        
	    return response.body();
	}
}
