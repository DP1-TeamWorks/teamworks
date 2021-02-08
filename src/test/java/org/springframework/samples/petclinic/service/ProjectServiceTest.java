package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.validation.DateIncoherenceException;
import org.springframework.samples.petclinic.validation.ManyProjectManagerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProjectServiceTest {
	@Autowired
	protected ProjectService projectService;
	@Autowired
	protected DepartmentService departmentService;
	@Autowired
	protected ParticipationService participationService;
	@Autowired
	protected UserTWService userService;

	@Test
	void shouldFindProjectWithCorrectId() {
		Project project2 = this.projectService.findProjectById(2);
		assertThat(project2.getName()).isEqualTo("Netrunning School");
		assertThat(project2.getDescription()).isEqualTo("Teach netrunning skills!");
	}

	@Test
	@Transactional
	public void shouldInsertProjectIntoDatabaseAndGenerateId() {

		Department department = departmentService.findDepartmentById(1);
		int size = department.getProjects().size();
		Project project = new Project();
		project.setName("Valorant");
		project.setDescription("Juego de riot");
		project.setDepartment(department);

		try {
			this.projectService.saveProject(project);

		} catch (DataAccessException ex) {
			Logger.getLogger(ProjectServiceTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		department.getProjects().add(project);
		departmentService.saveDepartment(department);
		department = departmentService.findDepartmentById(1);
		assertThat(department.getProjects().size() == size + 1).isTrue();
		assertThat(project.getId()).isNotNull();

	}

	@Test
	@Transactional
	void shouldUpdateProject() {
		Project project = this.projectService.findProjectById(1);
		String oldName = project.getName();
		String newName = oldName + "X";

		project.setName(newName);
		this.projectService.saveProject(project);
		;

		// retrieving new name from database
		project = this.projectService.findProjectById(1);
		;
		assertThat(project.getName()).isEqualTo(newName);
	}
	@Test
	@Transactional
	public void shouldNotInsertAProjectNullIntoDatabase() {

		Project project = new Project();
		assertThrows(Exception.class, ()-> {
			this.projectService.saveProject(project);
            });

	}

	@Test
	@Transactional
	void shouldDeleteProject() {

		projectService.deleteProjectById(2);
		Project project = this.projectService.findProjectById(2);
		assertThat(project).isNull();
	}

	@Test
	@Transactional
	void shouldfindUserProjects() {
		Department department = departmentService.findDepartmentById(1);
		Project project = new Project();
		project.setName("Valorant");
		project.setDescription("Juego de riot");
		project.setDepartment(department);
		this.projectService.saveProject(project);

		Participation participacion = new Participation();
		UserTW user = userService.findUserById(1);
		participacion.setUserTW(user);
		participacion.setProject(project);
		participacion.setIsProjectManager(false);
		try {
			participationService.saveParticipation(participacion);
		} catch (DataAccessException | ManyProjectManagerException | DateIncoherenceException e) {
			Logger.getLogger(ProjectServiceTest.class.getName()).log(Level.SEVERE, null, e);
		}
		List<UserTW> listaUser = projectService.findUserProjects(project.getId()).stream().collect(Collectors.toList());
		assertThat(listaUser.isEmpty()).isFalse();
	}

}
