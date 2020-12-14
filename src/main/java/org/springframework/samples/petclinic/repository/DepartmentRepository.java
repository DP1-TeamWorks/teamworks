package org.springframework.samples.petclinic.repository;

import java.util.Collection;


import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.*;

public interface DepartmentRepository extends Repository<Department, Integer> {
	    /**
	     * Save a <code>Department</code> to the data store, either inserting or updating it.
	     * @param departments the <code>Department</code> to save
	     * @see BaseEntity#isNew
	     */
	    void save(Department departments) throws DataAccessException;
	    
	    void deleteById(Integer id) throws DataAccessException;
	    
	    Department findById(Integer departmentId);
	    
	    @Query("SELECT u FROM Department u WHERE u.name LIKE :name%")
		public Collection<Department> findByName(@Param("name") String name);

	    Collection<Department> findAll() throws DataAccessException;;
	    


	}


