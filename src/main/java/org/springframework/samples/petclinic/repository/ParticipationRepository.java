package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;

public interface ParticipationRepository extends Repository<Participation, Integer> {
	void save(Participation participation) throws DataAccessException;

	void deleteById(Integer participationId) throws DataAccessException;

	Participation findById(Integer participationId) throws DataAccessException;

	@Query(value = "SELECT u FROM Participation u WHERE u.userTW.id = :userId and u.project.id= :projectId")
	public Collection<Participation> findParticipationByUserAndDeparment(@Param("userId") Integer userId,
			@Param("projectId") Integer projectId);

	@Query(value = "SELECT u FROM Participation u WHERE u.userTW.id = :userId and u.project.id= :projectId and u.finalDate=null")
	public Participation findCurrentParticipation(@Param("userId") Integer userId,
			@Param("projectId") Integer projectId);

	@Query(value = "SELECT u.project FROM Participation u WHERE u.userTW.id = :userId and u.finalDate=null")
	public Collection<Project> findMyProjects(@Param("userId") Integer userId);

	@Query(value = "SELECT u.project FROM Participation u WHERE u.userTW.id = :userId and u.project.department.id = :departmentId and u.finalDate=null")
	public Collection<Project> findMyDepartemntProjects(@Param("userId") Integer userId,
			@Param("departmentId") Integer departmentId);
}
