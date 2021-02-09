package org.springframework.samples.petclinic.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private MessageService messageService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, MessageService messageService) {
        this.projectRepository = projectRepository;
        this.messageService = messageService;
    }

    @Transactional
    public void saveProject(Project project) throws DataAccessException {
        projectRepository.save(project);
    }

    @Transactional
    public void deleteProjectById(Integer projectId)
        throws DataAccessException {

        Project project = projectRepository.findById(projectId);
        if (project == null)
            return;

        for (Tag t : project.getTags())
        {
            for (Message m : t.getMessages())
            {
                m.getTags().remove(t);
                messageService.saveMessage(m);
            }
        }

        projectRepository.deleteById(projectId);
    }

    @Transactional(readOnly = true)
    public Collection<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<Project> getProjectsByName(String name)
        throws DataAccessException {
        return projectRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Project findProjectById(Integer projectId)
        throws DataAccessException {
        return projectRepository.findById(projectId);
    }

    @Transactional(readOnly = true)
    public Collection<UserTW> findUserProjects(Integer projectId) {
        return projectRepository.findProjectUsers(projectId);
    }
}
