package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.UserTW;

public interface UserTWRepository extends Repository<UserTW, Integer>{
    /**
     * Save a <code>NewUser</code> to the data store, either inserting or updating it.
     * @param user the <code>Owner</code> to save
     * @see BaseEntity#isNew
     */
	
	
	void save(UserTW user) throws DataAccessException;

	void deleteById (Integer id) throws DataAccessException;
	
    public UserTW findById(Integer id);
    
    @Query("SELECT u FROM UserTW u WHERE u.name LIKE :name%")
    public Collection<UserTW> findByName(@Param("name") String name);
    
    @Query("SELECT u FROM UserTW u WHERE u.lastname LIKE :lastname%")
    public Collection<UserTW> findByLastName(@Param("lastname") String lastname);

    
    public Collection<UserTW> findAll() throws DataAccessException;
    
    @Query("SELECT u FROM UserTW u WHERE u.email = :email")
    public UserTW findbyEmail(@Param("email") String email);
}
