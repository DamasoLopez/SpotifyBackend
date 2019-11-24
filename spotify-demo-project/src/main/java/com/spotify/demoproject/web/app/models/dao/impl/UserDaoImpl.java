package com.spotify.demoproject.web.app.models.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.spotify.demoproject.web.app.models.entity.UserSearchTracks;

@Repository
public class UserDaoImpl {
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public <S extends UserSearchTracks> S save(S entity) {
		em.persist(entity);
		return null;
	}

	@Transactional
	public <S extends UserSearchTracks> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public Optional<UserSearchTracks> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Transactional
	public List<UserSearchTracks> findAll() {
		
		return em.createQuery("from UserSearchTracks").getResultList();
	}

	public Iterable<UserSearchTracks> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	public void delete(UserSearchTracks entity) {
		// TODO Auto-generated method stub
		
	}

	public void deleteAll(Iterable<? extends UserSearchTracks> entities) {
		// TODO Auto-generated method stub
		
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}
