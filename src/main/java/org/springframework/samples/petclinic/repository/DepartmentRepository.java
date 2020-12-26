package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.*;

public interface DepartmentRepository extends Repository<Department, Integer> {
	/**
	 * Save a <code>Department</code> to the data store, either inserting or
	 * updating it.
	 * 
	 * @param departments the <code>Department</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Department departments) throws DataAccessException;

	void deleteById(Integer id) throws DataAccessException;

	Department findById(Integer departmentId);

	Collection<Department> findAll() throws DataAccessException;

}
