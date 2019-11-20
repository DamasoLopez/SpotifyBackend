package com.spotify.demoproject.web.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spotify.demoproject.web.app.models.entity.UserSearchTracks;

public interface IUserDao extends CrudRepository<UserSearchTracks,Long>{

	

}
