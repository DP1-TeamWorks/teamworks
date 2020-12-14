package org.springframework.samples.petclinic.service;
/*
 * Copyright 2002-2013 the original author or authors.|
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class DepartmentService {
	private DepartmentRepository departmentRepository;
	

	@Autowired
	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
		
	}

	@Transactional
	public void saveDepartment(Department department) throws DataAccessException {
		departmentRepository.save(department);
	}
	
	@Transactional(readOnly = true)
	public Department findDepartmentById(Integer departmentId) throws DataAccessException {
		return departmentRepository.findById(departmentId);
	}
	
	@Transactional(readOnly = true)
	public Collection<Department> findDepartmentByName(String name) throws DataAccessException {
		return departmentRepository.findByName(name);
	}
	
	public void deleteDepartmentById(Integer departmentId) throws DataAccessException {
		departmentRepository.deleteById(departmentId);
	}

	@Transactional(readOnly = true)
	public Collection<Department> getAllDepartments() throws DataAccessException {
		return departmentRepository.findAll();
	}

	
}
