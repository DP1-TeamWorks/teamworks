package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.validation.DateIncoherenceException;
import org.springframework.samples.petclinic.validation.ManyProjectManagerException;
import org.springframework.samples.petclinic.validation.ManyTeamOwnerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ParticipationServiceTest {
    @Autowired
    protected ParticipationService participationService;
    @Autowired
    protected UserTWService userTWService;
    @Autowired
    protected ProjectService proyectService;

    @Test
    void shouldFindParticipationWithCorrectId() {
        Participation participationTest = this.participationService.findParticipationById(1);
        assertThat(participationTest.getProject().getName()).isEqualTo("Networking Solutions");
        assertThat(participationTest.getIsProjectManager()).isTrue();
    }
    @Test
    void shouldFindParticipationByUserIdAndProjectId() {
        List<Participation> participations = this.participationService.findParticipationByUserIdAndProjectId(2, 1).stream().collect(Collectors.toList());
        assertThat(participations.size()).isEqualTo(1);
       
    }
    @Test
    void shouldFindUserParticipationsByUserId() {
        List<Participation> userParticipation = this.participationService.findUserParticipations(2).stream()
                .collect(Collectors.toList());
        assertThat(userParticipation.size()).isEqualTo(4);
    }
    
    @Test
    void shouldFindCurrentParticipationByUserIdAndProjectId() {
        Participation participation = this.participationService.findCurrentParticipation(2, 1);
        assertThat(participation.getProject().getName()).isEqualTo("Networking Solutions");
        assertThat(participation.getUserTW().getName()).isEqualTo("Julia");
    }

    

    @Test
    void shouldFindUserCurrentParticipationByUserId() {
        List<Participation> userParticipation = this.participationService.findCurrentParticipationsUser(2).stream()
                .collect(Collectors.toList());
        assertThat(userParticipation.size()).isEqualTo(4);
    }

    @Test
    void shouldFindMyProjects() {
        List<Project> userProjects = this.participationService.findMyProjects(2).stream().collect(Collectors.toList());
        assertThat(userProjects.size()).isEqualTo(4);
    }
    @Test
    void shouldFindMyDepartmentProjects() {
    	List<Project> userDepartmentProjects = this.participationService.findMyDepartmentProjects(2, 1).stream().collect(Collectors.toList());
    	assertThat(userDepartmentProjects.size()).isEqualTo(4);
    }
    @Test
    void shouldFindCurrentParticipationsInProject() {
    	List<Participation> participationstProject = this.participationService.findCurrentParticipationsInProject(1).stream().collect(Collectors.toList());
    	assertThat(participationstProject.size()).isEqualTo(1);
    }
    @Test
    void shouldfindCurrentProjectManager() {
    	Participation projectManagerParticipation=this.participationService.findCurrentProjectManager(1);
    	assertThat(projectManagerParticipation.getUserTW().getName()).isEqualTo("Julia");
    	assertThat(projectManagerParticipation.getUserTW().getLastname()).isEqualTo("Fabra");
    }
    @Test
    @Transactional
    public void shouldInsertParticipationIntoDatabaseAndGenerateId() {
        UserTW user = userTWService.findUserById(2);
        Project project = proyectService.findProjectById(1);
        Participation participation = new Participation();
        participation.setProject(project);
        participation.setUserTW(user);
        user.getParticipation().add(participation);
        project.getParticipations().add(participation);
        participation.setIsProjectManager(false);
        try {
            this.participationService.saveParticipation(participation);
            this.proyectService.saveProject(project);
            this.userTWService.saveUser(user);
        } catch (DataAccessException | ManyProjectManagerException | DateIncoherenceException | ManyTeamOwnerException ex) {
            Logger.getLogger(ParticipationServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertThat(participation.getId()).isNotNull();
        assertThat(participation.getUserTW().getId()).isEqualTo(user.getId());
        assertThat(participation.getProject().getId()).isEqualTo(project.getId());
        assertThat(project.getParticipations().contains(participation)).isTrue();
        assertThat(user.getParticipation().contains(participation)).isTrue();

    }
    @Test
	@Transactional
	public void shouldNotInsertAParticipationNullIntoDatabase() {

		Participation participation = new Participation();
		assertThrows(Exception.class, ()-> {
			this.participationService.saveParticipation(participation);
            });

	}

    @Test
    @Transactional
    void shouldUpdateAndFinishParticipation() {
        Participation participation = this.participationService.findParticipationById(1);
        LocalDate ld = LocalDate.now();
        participation.setFinalDate(ld);
        try {
			this.participationService.saveParticipation(participation);
		} catch (DataAccessException | ManyProjectManagerException | DateIncoherenceException e) {
			 Logger.getLogger(ParticipationServiceTest.class.getName()).log(Level.SEVERE, null, e);
		}

        Participation updatedParticipation = this.participationService.findParticipationById(1);
        assertThat(updatedParticipation.getFinalDate()).isEqualTo(ld);
    }

    @Test
    @Transactional
    void shouldDeleteParticipation() {
        participationService.deleteParticipationById(2);
        Participation participation = this.participationService.findParticipationById(2);
        assertThat(participation).isNull();
    }

}
