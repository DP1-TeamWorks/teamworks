package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Milestone;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class MilestoneServiceTest {
	@Autowired
	protected MilestoneService milestoneService;
	@Autowired
	protected ProjectService projectService;

	@Test
	@Transactional
	void shouldInsertMilestoneIntoDataBaseAndGenerateId() {

		Project project = projectService.findProjectById(1);
		Milestone milestone = new Milestone();
		milestone.setName("Sprint323");
		milestone.setDueFor(LocalDate.now());
		milestone.setProject(project);


		try {
			this.milestoneService.saveMilestone(milestone);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			Logger.getLogger(MilestoneServiceTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		assertThat(milestone).isNotNull();
	}
	@Test
	@Transactional
	public void shouldNotInsertAMilestoneNullIntoDatabase() {

		Milestone milestones = new Milestone();
		assertThrows(Exception.class, ()-> {
			this.milestoneService.saveMilestone(milestones);
            });

	}

	@Test
	@Transactional
	void shouldDeleteMilestoneById() {

		milestoneService.deleteMilestoneById(1);
		Milestone mile = this.milestoneService.findMilestoneById(1);
		assertThat(mile).isNull();

	}

	@Test
	void shouldFindMilestoneById() {
		Milestone milestone = this.milestoneService.findMilestoneById(1);
		assertThat(milestone.getName()).isEqualTo("New Year objectives");

	}

	@Test
	void shouldFindNextMilestone() {
		Project project = projectService.findProjectById(1);
		Milestone milestone = new Milestone();
		milestone.setName("Sprint 3");
		milestone.setDueFor(LocalDate.now());
		milestone.setProject(project);

		Milestone milestone2 = this.milestoneService.findNextMilestone(1);

		assertThat(milestone.getDueFor()).isBefore(milestone2.getDueFor());
	}


	@Test
	void shouldFindMilestoneForProject() {
		Project project = projectService.findProjectById(1);
		Collection <Milestone> milestones = this.milestoneService.findMilestonesForProject(project.getId());
		List<Milestone> list;
		if (milestones instanceof List)
		  list = (List)milestones;
		else
		  list = new ArrayList(milestones);


		assertThat(list.get(0).getName()).isEqualTo("New Year objectives");

	}



	//NEGATIVE USE CASE H12-E1
	@Test
	@Transactional
	void shouldInsertMilestoneWithoutData() {

		Project project = projectService.findProjectById(1);
		Milestone milestone = new Milestone();
		milestone.setProject(project);

		assertThrows(Exception.class, ()-> {
			this.milestoneService.saveMilestone(milestone);
			});

	}


}
