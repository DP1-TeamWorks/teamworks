package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.*;

public interface MilestoneRepository extends Repository<Milestone, Integer> {

	void save(Milestone milestones) throws DataAccessException;

	void deleteById(Integer id) throws DataAccessException;

	Milestone findById(Integer milestoneId);
	
	@Query(value = "SELECT u FROM Milestone u WHERE u.name = :name")
	public Collection<Milestone> findMilestonesByName(@Param("name") Integer name);

	@Query(value = "SELECT u FROM Milestone u WHERE u.project.id = :projectId")
	public Collection<Milestone> findMilestonesByProject(@Param("projectId") Integer projectId);

	Collection<Milestone> findAll() throws DataAccessException;;

}
