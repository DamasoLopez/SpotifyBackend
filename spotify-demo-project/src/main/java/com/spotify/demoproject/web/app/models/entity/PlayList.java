package com.spotify.demoproject.web.app.models.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;


public class PlayList {
	

	    @JsonProperty("access_token")
	    @NotNull
	    private String accessToken;

	    @JsonProperty("refresh_token")
	    @NotNull
	    private String refreshToken;

	    @JsonProperty("playListName")
	    @NotBlank
	    private String playListName;
	    
	    @JsonProperty("userId")
	    @NotNull
	    private String userId;



		public String getAccessToken() {
			return accessToken;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}

		public String getRefreshToken() {
			return refreshToken;
		}

		public void setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
		}

		public String getPlayListName() {
			return playListName;
		}

		public void setPlayListName(String playListName) {
			this.playListName = playListName;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}
	    
	    
}
