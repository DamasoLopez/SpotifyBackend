package com.spotify.demoproject.web.app.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spotify.demoproject.web.app.models.dao.IUserDao;
import com.spotify.demoproject.web.app.models.entity.UserSearchTracks;

@Service
public class UserServicesImpl implements IUserServices{
	@Autowired
	IUserDao userDao;
	
	@Transactional(readOnly=true)
	public List<UserSearchTracks> findAll(){
		return (List<UserSearchTracks>) userDao.findAll();
	};
}
