package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Team;

public interface TeamRepository extends Repository<Team, Integer>{

	
	void save(Team team) throws DataAccessException;
	
	void deleteById (Integer teamId) throws DataAccessException;
	
    public Team findById(Integer id);
    
    @Query("SELECT u FROM Team u WHERE u.name LIKE :name%")
    public Collection<Team> findByName(@Param("name") String name);
    
    public Collection<Team> findAll() throws DataAccessException;
}
