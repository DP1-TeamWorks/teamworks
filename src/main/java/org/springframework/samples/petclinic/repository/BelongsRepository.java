package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Belongs;

public interface BelongsRepository extends Repository<Belongs, Integer>{
	void save(Belongs belongs) throws DataAccessException;
	void deleteById(Integer belongsId) throws DataAccessException;
	Belongs findById(Integer projectId) throws DataAccessException;
	
	
}
