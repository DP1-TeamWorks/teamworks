package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {
	private final DepartmentService departmentService;
	private final TeamService teamService;
	private final UserTWService userTWService;
	private final BelongsService belongsService;

	@Autowired
	public DepartmentController(DepartmentService departmentService, TeamService teamService,
			UserTWService userTWService, BelongsService belongsService) {
		this.departmentService = departmentService;
		this.teamService = teamService;
		this.userTWService = userTWService;
		this.belongsService = belongsService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/api/departments")
	public List<Department> getTeamDeparments(HttpServletRequest r) {
		List<Department> l = new ArrayList<>();
		Integer teamId = (Integer) r.getSession().getAttribute("teamId");
		l = teamService.findTeamById(teamId).getDepartments();
		return l;
	}

	@GetMapping(value = "/api/departments/my")
	public List<Department> getMyDeparments(HttpServletRequest r) {
		Integer userId = (Integer) r.getSession().getAttribute("userId");
		UserTW user = userTWService.findUserById(userId);

		List<Department> l = user.getBelongs().stream().filter(b -> b.getFinalDate() != null)
				.map(b -> b.getDepartment()).collect(Collectors.toList());
		return l;
	}

	@PostMapping(value = "/api/departments")
	public ResponseEntity<String> createDeparment(@RequestBody Department department, HttpServletRequest r) {

		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			Integer teamId = (Integer) r.getSession().getAttribute("teamId");

			UserTW user = userTWService.findUserById(userId);
			if (user.getRole().equals(Role.team_owner)) {

				Team team = teamService.findTeamById(teamId);
				department.setTeam(team);
				departmentService.saveDepartment(department);
				return ResponseEntity.ok("Department create");
			} else {
				return ResponseEntity.status(403).build();
			}

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}

	@DeleteMapping(value = "/api/departments")
	public ResponseEntity<String> deleteDeparments(@RequestParam(required = true) Integer departmentId,
			HttpServletRequest r) {

		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			UserTW user = userTWService.findUserById(userId);
			if (user.getRole().equals(Role.team_owner)) {
				departmentService.deleteDepartmentById(departmentId);
				return ResponseEntity.ok("Department delete");
			} else {
				return ResponseEntity.status(403).build();
			}

		} catch (DataAccessException d) {
			return ResponseEntity.notFound().build();
		}

	}

	// Belongs Requests
	@PostMapping(value = "/api/departments/belongs")
	public ResponseEntity<String> createBelongs(@RequestParam(required = true) Integer belongUserId,
			@RequestParam(required = true) Integer departmentId,
			@RequestParam(required = false) Boolean isDepartmentManager, HttpServletRequest r) {

		try {

			Belongs currentBelongs = belongsService.findCurrentBelongs(belongUserId, departmentId);
			UserTW user = userTWService.findUserById((Integer)r.getSession().getAttribute("userId"));
			Boolean teamOwner =  user.getRole().equals(Role.team_owner);
			if (currentBelongs == null) {
				Department department = departmentService.findDepartmentById(departmentId);
				UserTW belongUser = userTWService.findUserById(belongUserId);
				Belongs belongs = new Belongs();
				belongs.setDepartment(department);
				belongs.setUserTW(belongUser);
				belongs.setIsDepartmentManager(false);

				if (isDepartmentManager != null && teamOwner) {
					belongs.setIsDepartmentManager(isDepartmentManager);
				}
				belongsService.saveBelongs(belongs);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.badRequest().build();
			}

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}

	// Belongs Requests
	@DeleteMapping(value = "/api/departments/belongs/")
	public ResponseEntity<String> deleteBelongs(@RequestParam(required = true) Integer belongUserId,
			Integer departmentId, HttpServletRequest r) {

		try {
			Belongs belongs = belongsService.findBelongByUserIdAndDepartmentId(belongUserId, departmentId);
			belongs.setFinalDate(LocalDate.now());
			belongsService.saveBelongs(belongs);
			return ResponseEntity.ok().build();

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}
}
