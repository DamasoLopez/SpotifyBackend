package com.spotify.demoproject.web.app.models.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spotify.demoproject.web.app.models.dao.IUserDao;
import com.spotify.demoproject.web.app.models.dao.impl.UserDaoImpl;
import com.spotify.demoproject.web.app.models.entity.UserSearchTracks;

@Service
public class UserServicesImpl implements IUserServices{
	@Autowired
	IUserDao userDao;
	
	@Transactional(readOnly=true)
	public List<UserSearchTracks> findAll(){
		
		return (List<UserSearchTracks>) userDao.findAll();
		
	};
	@Transactional(readOnly=true)
	public void saveTrack(String email,String firstSong, String stringOfSong ){
		UserSearchTracks userSearchTracks=new UserSearchTracks();
		userSearchTracks.setDate(new Date());
		userSearchTracks.setEmail(email);
		userSearchTracks.setFirstSong(firstSong);
		userSearchTracks.setStringOfSong(stringOfSong);
		userDao.save(userSearchTracks);
		
		
	};
}
