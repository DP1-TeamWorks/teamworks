package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.*;
import java.util.Collection;


public interface NewUserRepository extends Repository<NewUser, String> {
    /**
     * Save a <code>NewUser</code> to the data store, either inserting or updating it.
     * @param user the <code>Owner</code> to save
     * @see BaseEntity#isNew
     */
    void save(NewUser user) throws DataAccessException;

    public NewUser findById(String id);

    public Collection<NewUser> findAll() throws DataAccessException;

}
