package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Interest;
import org.springframework.samples.petclinic.model.NewUser;


public interface InterestRepository extends Repository<NewUser, String> {
    /**
     * Save an <code>Owner</code> to the data store, either inserting or updating it.
     * @param interest the <code>Interest</code> to save
     * @see BaseEntity#isNew
     */
    void save(Interest interest) throws DataAccessException;

    //public Interest findById(String id);
}
