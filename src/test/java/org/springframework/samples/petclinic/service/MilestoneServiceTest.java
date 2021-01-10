package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
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
		milestone.setName("Sprint3");
		milestone.setDueFor(LocalDate.now());
		milestone.setProject(project);

		try {
			this.milestoneService.saveMilestone(milestone);
		} catch (DataAccessException ex) {
			Logger.getLogger(MilestoneServiceTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		assertThat(milestone).isNotNull();
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

}
