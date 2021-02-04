package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.DateIncoherenceException;
import org.springframework.samples.petclinic.validation.IdParentIncoherenceException;
import org.springframework.samples.petclinic.validation.ManyDepartmentManagerException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BelongsController {

	private final DepartmentService departmentService;
	private final UserTWService userTWService;
	private final BelongsService belongsService;

	@Autowired
	public BelongsController(DepartmentService departmentService, UserTWService userTWService,
			BelongsService belongsService) {
		this.departmentService = departmentService;
		this.userTWService = userTWService;
		this.belongsService = belongsService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	// Belongs Requests
	@PostMapping(value = "/api/departments/belongs")
	public ResponseEntity<String> createBelongs(@RequestParam(required = true) Integer belongUserId,
			@RequestParam(required = true) Integer departmentId,
			@RequestParam(required = false) Boolean isDepartmentManager, HttpServletRequest r) {

		try {
			log.info("Obteniendo el current belongs del usuario logeado");
			Belongs currentBelongs = belongsService.findCurrentBelongs(belongUserId, departmentId);
			log.info("Obteniendo el usuario logeado");
			UserTW user = userTWService.findUserById((Integer) r.getSession().getAttribute("userId"));
			log.info("Comprobando si el usuario es teamOwner");
			Boolean isTeamOwner = user.getRole().equals(Role.team_owner);
			log.info("Obteniendo el departamento");
			Department department = departmentService.findDepartmentById(departmentId);
			log.info("Obteniendo el usuario dle que se quiere crear el belongs");
			UserTW belonguser = userTWService.findUserById(belongUserId);

			if (!belonguser.getTeam().equals(user.getTeam())) {
				log.error("El user no pertenece al team");
				throw new IdParentIncoherenceException("Team", "User");
			}

			if (user.getTeam().equals(department.getTeam())) {
				log.error("El departamento no pertenece al team");
				throw new IdParentIncoherenceException("Team", "Department");
			}

			if (currentBelongs == null) {
				UserTW belongUser = userTWService.findUserById(belongUserId);
				Belongs belongs = new Belongs();
				belongs.setDepartment(department);
				belongs.setUserTW(belongUser);
				belongs.setIsDepartmentManager(false);

				if (isDepartmentManager != null && isTeamOwner) {
					belongs.setIsDepartmentManager(isDepartmentManager);
				}
				belongsService.saveBelongs(belongs);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.badRequest().body("Ya existe un belongs");
			}

		} catch (DataAccessException | ManyDepartmentManagerException | DateIncoherenceException
				| IdParentIncoherenceException d) {
			return ResponseEntity.badRequest().build();
		}

	}

	// Belongs Requests
	@DeleteMapping(value = "/api/departments/belongs")
	public ResponseEntity<String> deleteBelongs(@RequestParam(required = true) Integer belongUserId,
			Integer departmentId, HttpServletRequest r) {
		try {
			UserTW user = userTWService.findUserById((Integer) r.getSession().getAttribute("userId"));
			Boolean isTeamOwner = user.getRole().equals(Role.team_owner);
			Belongs belongs = belongsService.findCurrentBelongs(belongUserId, departmentId);
			if (belongs.getIsDepartmentManager() == false || isTeamOwner) {
				belongs.setFinalDate(LocalDate.now());
				belongsService.saveBelongs(belongs);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(403).build();
			}

		} catch (DataAccessException | ManyDepartmentManagerException | DateIncoherenceException d) {
			return ResponseEntity.badRequest().build();
		}

	}
}
