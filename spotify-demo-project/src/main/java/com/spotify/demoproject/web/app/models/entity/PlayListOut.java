package com.spotify.demoproject.web.app.models.entity;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

public class PlayListOut {
	 
	    private String name;

	   


	   
	    public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		
	    
}
