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
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DepartmentServiceTest {
	@Autowired
	protected DepartmentService departmentService;

	@Test
	@Transactional
	public void shouldInsertDepartmentIntoDatabaseAndGenerateId() {

		Department departments = new Department();
		departments.setName("Venta");
		departments.setDescription("Funcion: vender....");

		departmentService.saveDepartment(departments);
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

}
