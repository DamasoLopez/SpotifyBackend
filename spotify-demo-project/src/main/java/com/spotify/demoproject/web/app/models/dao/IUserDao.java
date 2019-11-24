package com.spotify.demoproject.web.app.models.dao;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import com.spotify.demoproject.web.app.models.entity.UserSearchTracks;
@Primary
public interface IUserDao extends CrudRepository<UserSearchTracks,Long>{

	public List<UserSearchTracks>findAll();
	
	public <S extends UserSearchTracks> S save(S entity);

}
