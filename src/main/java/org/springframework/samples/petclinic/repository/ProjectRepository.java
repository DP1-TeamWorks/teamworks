package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Project;

public interface ProjectRepository extends Repository<Project, Integer> {
	/**
     * Save a <code>Department</code> to the data store, either inserting or updating it.
     * @param departments the <code>Department</code> to save
     * @see BaseEntity#isNew
     */
    void save(Project project) throws DataAccessException;
    
    @Query("SELECT u FROM Project u WHERE u.name LIKE :name%")
	public Collection<Project> findByName(@Param("name") String name);
    
    Project findById(Integer projectId)throws DataAccessException;
    
	void deleteById(Integer projectId) throws DataAccessException;
    
    public Collection<Project> findAll() throws DataAccessException;

	
}
