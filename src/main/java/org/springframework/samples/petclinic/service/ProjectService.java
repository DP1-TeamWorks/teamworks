package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {
	private ProjectRepository projectRepository;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository=projectRepository;
	}
	
	@Transactional
	public void saveProject(Project project) throws DataAccessException {
			projectRepository.save(project);
	}
	@Transactional 
	public void deleteProjectById(Integer projectId) throws DataAccessException {
		projectRepository.deleteById(projectId);
	}
	@Transactional(readOnly = true)
	public Collection<Project> getAllProjects() {
        return projectRepository.findAll();
    }
	@Transactional(readOnly = true)
	public Collection<Project> getProjectsByName(String name) {
        return projectRepository.findByName(name);
    }
	@Transactional(readOnly = true)
	public Project findProjectById(Integer projectId) throws DataAccessException {
		return projectRepository.findById(projectId);
	}
	

}
