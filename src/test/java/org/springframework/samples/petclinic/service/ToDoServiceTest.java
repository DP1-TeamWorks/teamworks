package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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

		UserTW user = userService.findUserById(2);
		Milestone milestone = milestonService.findMilestoneById(1);
		ToDo toDo = new ToDo();
		toDo.setTitle("Mark this test as done");
		toDo.setAssignee(user);
		toDo.setMilestone(milestone);

		try {
			this.toDoService.saveToDo(toDo);
		} catch (DataAccessException ex) {
			Logger.getLogger(ToDoServiceTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		assertThat(toDo.getId()).isNotNull();
		assertThat(toDo.getAssignee()).isEqualTo(user);
		assertThat(toDo.getMilestone()).isEqualTo(milestone);

	}

	@Test
	void shouldFindDToDoWithCorrectId() {
		ToDo toDo = this.toDoService.findToDoById(1);
		assertThat(toDo.getTitle()).isEqualTo("Mark this as done");
		assertThat(toDo.getMilestone().getId()).isEqualTo(1);

	}

	@Test
	@Transactional
	void shouldDeleteToDo() {

		toDoService.deleteToDoById(3);
		ToDo toDo = this.toDoService.findToDoById(3);
		assertThat(toDo).isNull();
	}
	
	
	//NEGATIVE USE CASE H16-E1
	@Test
	@Transactional
	void shouldInsertToDoWithoutData() {
		ToDo toDo = new ToDo();	
		assertThrows(ConstraintViolationException.class, ()-> {
			toDoService.saveToDo(toDo);
			});
	}
	
	
	//NEGATIVE USE CASE 19-E1
	@Test
	@Transactional
	public void shouldInsertToDoWithNullUser() {

		//UserTW user = userService.findUserById(2);
		Milestone milestone = milestonService.findMilestoneById(1);
		ToDo toDo = new ToDo();
		toDo.setTitle("New ToDo Without an user assigned");
		toDo.setAssignee(null);
		toDo.setMilestone(milestone);

		assertThrows(DataIntegrityViolationException.class, ()-> {
			toDoService.saveToDo(toDo);
			});
	}
	
	//TODO:
	//NEGATIVE USE CASE 20-E1


}
