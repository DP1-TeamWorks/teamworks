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
import org.springframework.samples.petclinic.validation.DateIncoherenceException;
import org.springframework.samples.petclinic.validation.IdParentIncoherenceException;
import org.springframework.samples.petclinic.validation.ManyProjectManagerException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
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
			@RequestParam(required = true) Integer projectId,
			@RequestParam(required = false) Boolean willBeProjectManager, HttpServletRequest r) {

		try {
			log.info("Creando participacion entre el user con id "+participationUserId+" y el proyecto con id "+projectId);
			
			UserTW user = userTWService.findUserById((Integer) r.getSession().getAttribute("userId"));
			Project project = projectService.findProjectById(projectId);
			Participation userCurrentParticipation = participationService.findCurrentParticipation(participationUserId,
					projectId);
			Belongs userCurrentBelongs = belongsService.findCurrentBelongs(participationUserId,
					project.getDepartment().getId());

			Participation managerParticipation = participationService.findCurrentParticipation(user.getId(), projectId);
			log.info("Comprobando que el proyecto y el usuario pertenecen al mismo team");
			if (project.getDepartment().getTeam().equals(user.getTeam()))
				throw new IdParentIncoherenceException("Team", "Project");
			log.info("Comprobando que el usuario pertenzca al departamento del proyecto");
			if (userCurrentBelongs == null)
				throw new IdParentIncoherenceException("Department", "User");
			log.info("Comprobando que el usuario no tiene ninguna participacion actual");
			if (userCurrentParticipation == null) {
				UserTW participationUser = userTWService.findUserById(participationUserId);
				Participation participation = new Participation();
				participation.setProject(project);
				participation.setUserTW(participationUser);
				participation.setIsProjectManager(false);
				// Solo puedes asignar el rol de project manager si eres teamOwner o
				// departmentManager
				if (willBeProjectManager != null) {
					participation.setIsProjectManager(willBeProjectManager);
					if (managerParticipation != null) {
						managerParticipation.setIsProjectManager(!willBeProjectManager);
						participationService.saveParticipation(managerParticipation);
					}
				}
				log.info("Guardando participacion");
				participationService.saveParticipation(participation);
				log.info("Participacion guardada con exito");
				return ResponseEntity.ok().build();
			} else {
				log.error("Existe ya una participacion");
				return ResponseEntity.badRequest()
						.body("Ya existe una participacion");
			}
		} catch (DataAccessException | ManyProjectManagerException | DateIncoherenceException
				| IdParentIncoherenceException d) {
			log.error("Error: "+d.getMessage());
			return ResponseEntity.badRequest().body(d.getMessage());
		}

	}

	@DeleteMapping(value = "/api/projects/participation")
	public ResponseEntity<String> deleteParticipation(@RequestParam(required = true) Integer participationUserId,
			@RequestParam(required = true) Integer projectId, HttpServletRequest r) {
		try {
			log.info("Borrando participacion entre el user con id "+participationUserId+" y el proyecto con id "+projectId);
			UserTW user = userTWService.findUserById((Integer) r.getSession().getAttribute("userId"));
			Participation participation = participationService.findCurrentParticipation(participationUserId, projectId);
			Project project = participation.getProject();
			Boolean isDepartmentManager = belongsService
					.findCurrentBelongs(user.getId(), project.getDepartment().getId()).getIsDepartmentManager();
			Boolean isTeamOwner = user.getRole().equals(Role.team_owner);
			Boolean isProjectManager = participation.getIsProjectManager();
			log.info("Comprobando que el usuario tiene permisos");
			if (isProjectManager == false || (isDepartmentManager || isTeamOwner)) {
				participation.setFinalDate(LocalDate.now());
				log.info("Borrando participacion");
				participationService.saveParticipation(participation);
				log.info("Participacion borrada correctamente");
				return ResponseEntity.ok("Participation delete");
			} else {
				log.error("");
				return ResponseEntity.status(403).build();
			}

		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}
}
