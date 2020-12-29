package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Message;

public interface MessageRepository extends Repository<Message, Integer> {
	/**
	 * Save a <code>Department</code> to the data store, either inserting or
	 * updating it.
	 * 
	 * @param departments the <code>Department</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Message message) throws DataAccessException;

	void deleteById(Integer id) throws DataAccessException;

	Message findById(Integer messageId);

	Collection<Message> findAll() throws DataAccessException;

}

