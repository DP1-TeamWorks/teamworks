package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Milestone;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ToDoServiceTest {
	@Autowired
	protected ToDoService toDoService;
	@Autowired
	protected MilestoneService milestonService;
	@Autowired
	protected UserTWService userService;

	@Test
	@Transactional
	public void shouldInsertToDoIntoDatabaseAndGenerateId() {

		UserTW users = userService.findUserById(3);
		Milestone milestone = milestonService.findMilestoneById(2);
		ToDo todos = new ToDo();
		todos.setTitle("Mark this as in progress");
		todos.setAssignee(users);
		todos.setMilestone(milestone);

		toDoService.saveToDo(todos);
		try {
			this.toDoService.saveToDo(todos);
		} catch (DataAccessException ex) {
			Logger.getLogger(ToDoServiceTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		assertThat(todos.getId()).isNotNull();

	}

	@Test
	void shouldFindDToDoWithCorrectId() {
		ToDo todos1 = this.toDoService.findToDoById(1);
		assertThat(todos1.getTitle()).startsWith("Mark this as done");
		assertThat(todos1.getMilestone()).isEqualTo(1);

	}

	@Test
	@Transactional
	void shouldDeleteToDo() {

		toDoService.deleteToDoById(4);
		ToDo todos = this.toDoService.findToDoById(4);
		assertThat(todos).isNull();
	}

}
