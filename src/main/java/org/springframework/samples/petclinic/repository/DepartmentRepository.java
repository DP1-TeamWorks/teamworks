package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.*;

import java.util.Collection;
import java.util.Optional;

public interface DepartmentRepository extends Repository<Department, String> {
	    /**
	     * Save a <code>Department</code> to the data store, either inserting or updating it.
	     * @param departments the <code>Department</code> to save
	     * @see BaseEntity#isNew
	     */
	    void save(Department departments) throws DataAccessException;

	    public Department findById(String name);
	    


	}


