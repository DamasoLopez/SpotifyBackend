package com.spotify.demoproject.web.app.models.dao.impl;

import java.io.IOException;
import java.net.Authenticator;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;



@Service
public class GetUserProfileDaoImpl  {
	private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
 
public Map<String,String> getUser(String token,String AccesToken)throws Exception{
	String uri = "https://api.spotify.com/v1/me";
	

	
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .header("Authorization", "Bearer "+AccesToken)
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
  
    return HttpRequest.BodyPublishers.ofString(builder.toString());
}
}
