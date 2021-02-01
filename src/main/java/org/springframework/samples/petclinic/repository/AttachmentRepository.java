package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Attachment;

public interface AttachmentRepository extends Repository<Attachment, Integer>{
	
	void save(Attachment attatchments) throws DataAccessException;

	void deleteById(Integer id) throws DataAccessException;

	Attachment findById(Integer attatchmentId);

	Collection<Attachment> findAll() throws DataAccessException;

}
