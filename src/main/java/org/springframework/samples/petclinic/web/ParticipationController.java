package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.ManyProjectManagerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ParticipationController {
	private final ProjectService projectService;
	private final ParticipationService participationService;
	private final UserTWService userTWService;
	private final BelongsService belongsService;

	@Autowired
	public ParticipationController(ProjectService projectService, ParticipationService participationService,
			UserTWService userTWService, BelongsService belongsService) {
		this.projectService = projectService;
		this.participationService = participationService;
		this.userTWService = userTWService;
		this.belongsService = belongsService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
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
			Belongs currentBelongs = belongsService.findCurrentBelongs(participationUserId, project.getDepartment().getId());
			Belongs belongs = belongsService.findCurrentBelongs(user.getId(), project.getDepartment().getId());
			Boolean isTeamOwner = user.getRole().equals(Role.team_owner);
			// Comprueba si existe una participacion
			if (currentParticipation == null && currentBelongs!=null && (belongs != null||isTeamOwner)) {
				UserTW participationUser = userTWService.findUserById(participationUserId);
				Participation participation = new Participation();
				participation.setProject(project);
				participation.setUserTW(participationUser);
				participation.setIsProjectManager(false);
				// Solo puedes asignar el rol de project manager si eres teamOwner o
				// departmentManager
				if (isProjectManager != null && (isTeamOwner || belongs.getIsDepartmentManager())) {
					participation.setIsProjectManager(isProjectManager);
				}
				participationService.saveParticipation(participation);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.badRequest().body("Ya existe una participacion o el usuario no pertenece al departamento");
			}
		} catch (DataAccessException|ManyProjectManagerException d) {
			return ResponseEntity.badRequest().body(d.getMessage());
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
