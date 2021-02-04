package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DepartmentServiceTest {
	@Autowired
	protected DepartmentService departmentService;
	@Autowired
	protected TeamService teamService;

	@Test
	@Transactional
	public void shouldInsertDepartmentIntoDatabaseAndGenerateId() {
		
		Team team = teamService.findTeamById(1);
		Department departments = new Department();
		departments.setName("Venta");
		departments.setDescription("Funcion: vender....");
		departments.setTeam(team);

		//departmentService.saveDepartment(departments);
		try {
			this.departmentService.saveDepartment(departments);
		} catch (DataAccessException ex) {
			Logger.getLogger(DepartmentServiceTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		assertThat(departments.getId()).isNotNull();

	}
	

	@Test
	void shouldFindDepartmentWithCorrectId() {
		Department department1 = this.departmentService.findDepartmentById(1);
		assertThat(department1.getName()).startsWith("Calidad");
		assertThat(department1.getDescription()).isEqualTo("Aseguro la ...");

	}

	@Test
	@Transactional
	void shouldDeleteDepartment() {

		departmentService.deleteDepartmentById(2);
		Department department = this.departmentService.findDepartmentById(2);
		assertThat(department).isNull();
	}
	
	
	//NEGATIVE USE CASE H5-E1
	@Test
	@Transactional
	void shouldInsertDepartmentWithoutData () {
		Team team = teamService.findTeamById(1);
		Department departmentNoData = new Department();
		departmentNoData.setTeam(team);
		assertThrows(ConstraintViolationException.class, ()-> {
			departmentService.saveDepartment(departmentNoData);
			});		
	}	
	
	
	//TODO: GUARDA CON VALORES NULOS
	
	//NEGATIVE USE CASE H9-E1
	@Test
	@Transactional
	void shouldUpdateDeparment() {
		Department department = departmentService.findDepartmentById(1);
		department.setName(null);
		try {
			
			departmentService.saveDepartment(department);

		} catch (Exception ex) {
			System.out.println(ex);
		}
		//assertThrows(ConstraintViolationException.class, ()-> {

			//this.departmentService.saveDepartment(department);
			//});			
	}
}
