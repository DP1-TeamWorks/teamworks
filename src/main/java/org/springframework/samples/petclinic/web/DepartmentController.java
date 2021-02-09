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
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.*;
import org.springframework.samples.petclinic.validation.DepartmentValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartmentController {
	private final DepartmentService departmentService;
	private final TeamService teamService;
	private final BelongsService belongsService;
	private final DepartmentValidator departmentValidator;
	private final ParticipationService participationService;

	@Autowired
	public DepartmentController(DepartmentService departmentService, ParticipationService participationService, TeamService teamService, BelongsService belongsService, DepartmentValidator departmentValidator) {

		this.departmentService = departmentService;
		this.teamService = teamService;
		this.belongsService = belongsService;
		this.departmentValidator = departmentValidator;
		this.participationService = participationService;
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



	@GetMapping(value = "/api/departments/mine")
	public List<Department> getMyDeparments(HttpServletRequest r) {
		Integer userId = (Integer) r.getSession().getAttribute("userId");
		List<Department> myDpts = belongsService.findMyDepartments(userId).stream().collect(Collectors.toList());
		// for every department remove projects the user isnt a member in
        List<Project> participations = participationService.findCurrentParticipationsUser(userId)
            .stream().map(x -> x.getProject()).collect(Collectors.toList());
        for (Department d : myDpts)
        {
            List<Project> projectsWhereUserParticipates = new ArrayList<>();
            for (Project p : d.getProjects())
            {
                if (participations.contains(p))
                    projectsWhereUserParticipates.add(p);
            }
            d.setProjects(projectsWhereUserParticipates);
        }

		return myDpts;
	}

	@PostMapping(value = "/api/departments/create")
	public ResponseEntity<String> createDeparment(@Valid @RequestBody Department department, HttpServletRequest r) {
		try {
			Integer teamId = (Integer) r.getSession().getAttribute("teamId");
			Team team = teamService.findTeamById(teamId);
			if (department.getDescription() == null)
				department.setDescription("This is a brief description of the department");
			department.setTeam(team);
			departmentService.saveDepartment(department);

			return ResponseEntity.ok("Department create");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().body("alreadyexists");
		}
	}

	@PatchMapping(value = "/api/departments/update")
	public ResponseEntity<String> updateDepartment(@RequestBody Department department, HttpServletRequest r,
			BindingResult errors) {
		try {
			// log.info("Validando department con id:"+department.getId());
			departmentValidator.validate(department, errors);
			Department dbDepartment = departmentService.findDepartmentById(department.getId());
			if (errors.hasErrors() || dbDepartment == null)
				return ResponseEntity.badRequest().body(errors.getAllErrors().toString());

			// TODO Validate department
			/*
			 * if (department.getName() == "" || department.getDescription() == "") return
			 * ResponseEntity.badRequest().build();
			 */

			if (department.getName() != null)
				dbDepartment.setName(department.getName());
			if (department.getDescription() != null)
				dbDepartment.setDescription(department.getDescription());

			departmentService.saveDepartment(dbDepartment);

			return ResponseEntity.ok("Department updated");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().body("invalid id");
		}
	}

	@DeleteMapping(value = "/api/departments/{id}/delete")
	public ResponseEntity<String> deleteDeparment(@PathVariable(required = true) Integer id, HttpServletRequest r) {
		try {
			departmentService.deleteDepartmentById(id);
			return ResponseEntity.ok("Department delete");

		} catch (DataAccessException d) {
			return ResponseEntity.notFound().build();
		}
	}

}
