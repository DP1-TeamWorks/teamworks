package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.UserTW;

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
	// @Query(value="SELECT m FROM MESSAGE_RECIPIENTS, MESSAGE m WHERE :userId =
	// MESSAGE_RECIPIENTS.RECIPIENTS_ID AND :userId = MESSAGE.ID" )
	// SELECT * FROM Message, MESSAGE_RECIPIENTS WHERE :userId =
	// MESSAGE_RECIPIENTS.RECIPIENTS_ID
	// @Query(value="SELECT m FROM Message m WHERE :userId in m.recipients" )

	@Query(value = "SELECT m FROM Message m WHERE :userTW MEMBER OF m.recipients")
	public Collection<Message> findMessagesByUserId(@Param("userTW") UserTW userTW);

	@Query(value = "SELECT m FROM Message m WHERE m.sender.id = :userId")
	public Collection<Message> findMessagesSentByUserId(@Param("userId") Integer userId);

	@Query(value = "SELECT m FROM Message m WHERE :userTW MEMBER OF m.recipients AND :tag MEMBER OF m.tags")
	public Collection<Message> findMessagesByTag(@Param("userTW") UserTW userTW, @Param("tag") Tag tag);

	@Query(value = "SELECT m FROM Message m WHERE :userTW MEMBER OF m.recipients AND ( :search MEMBER OF m.tags"
			+ " OR m.sender.email LIKE :search" + " OR m.sender.name LIKE :search"
			+ " OR m.sender.lastname LIKE :search" + " OR m.subject LIKE :search" + " OR m.text LIKE :search)")
	public Collection<Message> findMessagesBySearch(UserTW user, String search);

}
