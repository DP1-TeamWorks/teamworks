package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.ProjectService;
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
public class ProjectController {
	private final DepartmentService departmentService;
	private final ProjectService projectService;
	private final ParticipationService participationService;
	private final UserTWService userTWService;
	private final BelongsService belongsService;

	@Autowired
	public ProjectController(DepartmentService departmentService, ProjectService projectService,
			ParticipationService participationService, UserTWService userTWService, BelongsService belongsService) {
		this.departmentService = departmentService;
		this.projectService = projectService;
		this.participationService = participationService;
		this.userTWService = userTWService;
		this.belongsService = belongsService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/api/projects")
	public List<Project> getProjects(@RequestParam(required = true) Integer departmentId) {
		List<Project> l = new ArrayList<>();
		l = departmentService.findDepartmentById(departmentId).getProjects();
		return l;
	}

	@GetMapping(value = "/api/projects/mine")
	public List<Project> getMyProjects(HttpServletRequest r, @RequestParam(required = true) Integer departmentId) {

		List<Project> l = new ArrayList<>();
		Integer userId = (Integer) r.getSession().getAttribute("userId");
		l = participationService.findMyDepartemntProjects(userId, departmentId).stream().collect(Collectors.toList());
		return l;

	}

	@PostMapping(value = "/api/projects")
	public ResponseEntity<String> postProjects(@RequestParam(required = true) Integer departmentId,
			@RequestBody Project project) {

		try {
			Department depar = departmentService.findDepartmentById(departmentId);
			project.setDepartment(depar);
			projectService.saveProject(project);
			return ResponseEntity.ok("Project create");
		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}

	// TODO: discutir departmentID (INTERCEPTOR)
	@DeleteMapping(value = "/api/projects")
	public ResponseEntity<String> deleteProjects(@RequestParam(required = true) Integer departmentId,
			@RequestParam(required = true) Integer projectId) {
		try {
			projectService.deleteProjectById(projectId);
			return ResponseEntity.ok("Project delete");
		} catch (DataAccessException d) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(value = "/api/projects/participation")
	public ResponseEntity<String> postParticipation(@RequestParam(required = true) Integer participationUserId,
			@RequestParam(required = true) Integer projectId, @RequestParam(required = false) Boolean isProjectManager,
			HttpServletRequest r) {

		try {
			Participation currentParticipation = participationService.findCurrentParticipation(participationUserId,
					projectId);
			UserTW user = userTWService.findUserById((Integer) r.getSession().getAttribute("userId"));
			Project project = projectService.findProjectById(projectId);
			Belongs belongs = belongsService.findCurrentBelongs(user.getId(), project.getDepartment().getId());
			Boolean isTeamOwner = user.getRole().equals(Role.team_owner);

			// Comprueba si existe una participacion
			if (currentParticipation == null && belongs != null) {
				UserTW participationUser = userTWService.findUserById(participationUserId);
				Participation participation = new Participation();
				participation.setProject(project);
				participation.setUserTW(participationUser);
				participation.setIsProjectManager(false);
				// Solo puedes asignar el rol de project manager si eres teamOwner o
				// departmentManager
				if (isProjectManager != null && (belongs.getIsDepartmentManager() || isTeamOwner)) {
					participation.setIsProjectManager(isProjectManager);
				}
				participationService.saveParticipation(participation);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.badRequest().body("Ya existe una participacion");
			}
		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}

	@DeleteMapping(value = "/api/projects/participation")
	public ResponseEntity<String> deleteParticipation(@RequestParam(required = true) Integer participationUserId,
			@RequestParam(required = true) Integer projectId, HttpServletRequest r) {
		try {
			UserTW user = userTWService.findUserById((Integer) r.getSession().getAttribute("userId"));
			Participation participation = participationService.findCurrentParticipation(participationUserId, projectId);
			Project project = participation.getProject();
			Boolean isDepartmentManager = belongsService
					.findCurrentBelongs(user.getId(), project.getDepartment().getId()).getIsDepartmentManager();
			Boolean isTeamOwner = user.getRole().equals(Role.team_owner);
			Boolean isProjectManager = participation.getIsProjectManager();

			if (isProjectManager == false || (isDepartmentManager || isTeamOwner)) {
				participation.setFinalDate(LocalDate.now());
				participationService.saveParticipation(participation);
				return ResponseEntity.ok("Participation delete");
			} else {
				return ResponseEntity.status(403).build();
			}

		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}

}
