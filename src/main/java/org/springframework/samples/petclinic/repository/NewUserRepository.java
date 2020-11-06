package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.NewUser;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;

import java.util.Optional;


public interface NewUserRepository extends Repository<NewUser, String> {
    /**
     * Save an <code>Owner</code> to the data store, either inserting or updating it.
     * @param user the <code>Owner</code> to save
     * @see BaseEntity#isNew
     */
    void save(NewUser user) throws DataAccessException;

    public NewUser findById(String id);
}
