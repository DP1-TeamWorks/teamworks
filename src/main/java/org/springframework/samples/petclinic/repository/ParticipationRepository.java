package org.springframework.samples.petclinic.repository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Participation;


public interface ParticipationRepository extends Repository<Participation, Integer>{
	void save(Participation participation) throws DataAccessException;
	void deleteById(Integer participationId) throws DataAccessException;
	Participation findById(Integer participationId) throws DataAccessException;
	
	
}
