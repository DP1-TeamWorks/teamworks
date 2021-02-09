package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
	private DepartmentRepository departmentRepository;
	private MessageService messageService;

	@Autowired
	public DepartmentService(DepartmentRepository departmentRepository, MessageService messageService) {
		this.departmentRepository = departmentRepository;
		this.messageService = messageService;
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

        Department department = departmentRepository.findById(departmentId);
        if (department == null)
            return;

        Collection<Tag> tags = department
            .getProjects()
            .stream()
            .map(x -> x.getTags())
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        for (Tag t : tags)
        {
            for (Message m : t.getMessages())
            {
                m.getTags().remove(t);
                messageService.saveMessage(m);
            }
        }

	    departmentRepository.deleteById(departmentId);
	}

	// No es necesario y creo q tampoco tiene sentido
	@Transactional(readOnly = true)
	public Collection<Department> getAllDepartments() throws DataAccessException {
		return departmentRepository.findAll();
	}@Transactional(readOnly = true)
	public Collection<UserTW> findDepartmentUsers(Integer departmentId) {
		return departmentRepository.findDepartmentUsers(departmentId);
	}

}
