package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value="/api/projects/participation")
    public ResponseEntity<Collection<Participation>> getParticipationsFromProject(@RequestParam(required = true) Integer projectId, HttpServletRequest r)
    {
        try
        {
            Collection<Participation> belongs = participationService.findCurrentParticipationsInDepartment(projectId);
            return ResponseEntity.ok(belongs);
        } catch (DataAccessException e)
        {
            return ResponseEntity.badRequest().build();
        }
    }

	@PostMapping(value = "/api/projects/participation")
	public ResponseEntity<String> postParticipation(@RequestParam(required = true) Integer participationUserId,
			@RequestParam(required = true) Integer projectId,
			@RequestParam(required = false) Boolean willBeProjectManager, HttpServletRequest r) {

		try {
			UserTW user = userTWService.findUserById((Integer) r.getSession().getAttribute("userId"));

			Project project = projectService.findProjectById(projectId);
			Participation userCurrentParticipation = participationService.findCurrentParticipation(participationUserId,
					projectId);
			Belongs userCurrentBelongs = belongsService.findCurrentBelongs(participationUserId,
					project.getDepartment().getId());

			Participation managerParticipation = participationService.findCurrentParticipation(user.getId(), projectId);

			if (!project.getDepartment().getTeam().equals(user.getTeam()))
				throw new IdParentIncoherenceException("Team", "Project");

			if (userCurrentBelongs == null)
				throw new IdParentIncoherenceException("Department", "User");

			Integer departmentId;
			Belongs managerBelongs = null;
			if (managerParticipation != null)
            {
                departmentId = managerParticipation.getProject().getDepartment().getId();
                managerBelongs = belongsService.findCurrentBelongs(user.getId(), departmentId);
            }

			if (userCurrentParticipation == null || (willBeProjectManager != null && userCurrentParticipation.getIsProjectManager() != willBeProjectManager)) {
				UserTW participationUser = userTWService.findUserById(participationUserId);
				Participation participation = new Participation();
				participation.setProject(project);
				participation.setUserTW(participationUser);
				participation.setIsProjectManager(false);
				if (willBeProjectManager != null && managerBelongs == null && user.getRole().equals(Role.team_owner))
                {
                    // Solo puedes asignar el rol de project manager si eres teamOwner o dptManager
                    return ResponseEntity.badRequest().build();
                }

                if (userCurrentParticipation != null)
                {
                    // End the previous participation
                    userCurrentParticipation.setFinalDate(LocalDate.now());
                    participationService.saveParticipation(userCurrentParticipation);
                }

				if (willBeProjectManager != null && willBeProjectManager) {
					participation.setIsProjectManager(true);
					Participation projectManagerParticipation = participationService.findCurrentProjectManager(projectId);
					if (projectManagerParticipation != null && projectManagerParticipation.getUserTW().equals(user)) {
						projectManagerParticipation.setFinalDate(LocalDate.now());
						participationService.saveParticipation(projectManagerParticipation);
					}
				}
				participationService.saveParticipation(participation);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.badRequest().body("alreadyexists");
			}
		} catch (DataAccessException | ManyProjectManagerException | DateIncoherenceException
				| IdParentIncoherenceException d) {
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
