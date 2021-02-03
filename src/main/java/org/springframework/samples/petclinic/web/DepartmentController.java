package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Department;
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
	private final BelongsService belongsService;

	@Autowired
	public DepartmentController(DepartmentService departmentService, TeamService teamService,
			UserTWService userTWService, BelongsService belongsService) {
		this.departmentService = departmentService;
		this.teamService = teamService;
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

	@GetMapping(value = "/api/department/users")
	public List<UserTW> getTeamUser(HttpServletRequest r, @RequestParam(required = true) Integer departmentId) {
		List<UserTW> l = new ArrayList<>();
		l = departmentService.findDepartmentUsers(departmentId).stream().collect(Collectors.toList());
		return l;
	}

	@GetMapping(value = "/api/departments/mine")
	public List<Department> getMyDeparments(HttpServletRequest r) {
		Integer userId = (Integer) r.getSession().getAttribute("userId");
		List<Department> l = belongsService.findMyDepartments(userId).stream().collect(Collectors.toList());
		return l;
	}

	@PostMapping(value = "/api/departments")
	public ResponseEntity<String> createDeparment(@Valid @RequestBody Department department, HttpServletRequest r) {
		try {
			Integer teamId = (Integer) r.getSession().getAttribute("teamId");
			Team team = teamService.findTeamById(teamId);

			department.setTeam(team);
			departmentService.saveDepartment(department);

			return ResponseEntity.ok("Department create");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping(value = "/api/departments")
	public ResponseEntity<String> deleteDeparment(@RequestParam(required = true) Integer departmentId,
			HttpServletRequest r) {
		try {
			departmentService.deleteDepartmentById(departmentId);
			return ResponseEntity.ok("Department delete");

		} catch (DataAccessException d) {
			return ResponseEntity.notFound().build();
		}
	}

}
