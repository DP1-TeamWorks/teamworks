package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Project;

public interface ProjectRepository extends Repository<DepartmentRepository, String> {
	/**
     * Save a <code>Department</code> to the data store, either inserting or updating it.
     * @param departments the <code>Department</code> to save
     * @see BaseEntity#isNew
     */
    void save(Project project) throws DataAccessException;
}
