package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
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

	@Query(value="SELECT m FROM Message m WHERE :userId in m.recipients" )
	public Collection<Message> findMessagesByUserId(@Param("userId") Integer userId);
	
	@Query(value="SELECT m FROM Message m WHERE m.sender = :userId" )
	public Collection<Message> findMessagesSentByUserId(@Param("userId") Integer userId);
	
	
	@Query(value="SELECT m FROM Message m WHERE :userId in m.recipients and :tagId in m.tags")
	public Collection<Message> findMessagesByTag(@Param("userId") Integer userId, @Param("tagId") Integer tagId);
	

}

