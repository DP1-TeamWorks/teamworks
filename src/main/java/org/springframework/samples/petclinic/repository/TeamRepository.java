package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;

public interface TeamRepository extends Repository<Team, Integer> {

    void save(Team team) throws DataAccessException;

    void deleteById(Integer teamId) throws DataAccessException;

    public Team findById(Integer id);
    
    

    @Query("SELECT u FROM Team u WHERE u.name LIKE :name%")
    public Collection<Team> findByName(@Param("name") String name);
    

    @Query("SELECT u FROM Team u WHERE u.identifier LIKE :identifier%")
    public Collection<Team> findByidentifier(@Param("identifier") String identifier);
    

    public Collection<Team> findAll() throws DataAccessException;
}
