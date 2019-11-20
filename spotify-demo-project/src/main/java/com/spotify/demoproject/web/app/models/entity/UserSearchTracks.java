package com.spotify.demoproject.web.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class UserSearchTracks implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String email;
	
	private String stringOfSong;
	
	private String firstSong;
	
	@Column(name="")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStringOfSong() {
		return stringOfSong;
	}
	public void setStringOfSong(String stringOfSong) {
		this.stringOfSong = stringOfSong;
	}
	public String getFirstSong() {
		return firstSong;
	}
	public void setFirstSong(String firstSong) {
		this.firstSong = firstSong;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
