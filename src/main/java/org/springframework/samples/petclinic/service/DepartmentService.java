package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

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

	@Transactional
	public void deleteDepartmentById(Integer departmentId) throws DataAccessException {
		departmentRepository.deleteById(departmentId);
	}

	// No es necesario y creo q tampoco tiene sentido
	@Transactional(readOnly = true)
	public Collection<Department> getAllDepartments() throws DataAccessException {
		return departmentRepository.findAll();
	}

}
