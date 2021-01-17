package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Attatchment;

public interface AttatchmentRepository extends Repository<Attatchment, Integer>{
	
	void save(Attatchment attatchments) throws DataAccessException;

	void deleteById(Integer id) throws DataAccessException;

	Attatchment findById(Integer attatchmentId);

	Collection<Attatchment> findAll() throws DataAccessException;

}
