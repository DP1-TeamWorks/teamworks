package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.enums.Role;
import org.springframework.samples.petclinic.service.*;
import org.springframework.samples.petclinic.validation.DateIncoherenceException;
import org.springframework.samples.petclinic.validation.IdParentIncoherenceException;
import org.springframework.samples.petclinic.validation.ManyProjectManagerException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value="/api/projects/participation")
    public ResponseEntity<Collection<Participation>> getParticipationsFromProject(@RequestParam(required = true) Integer projectId, HttpServletRequest r)
    {
        try
        {
            Collection<Participation> participation = participationService.findCurrentParticipationsInProject(projectId).stream().sorted(Comparator.comparing(Participation::getLastname).thenComparing(Participation::getName)).collect(Collectors.toList());
            return ResponseEntity.ok(participation);
        } catch (DataAccessException e)
        {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/api/projects/participation/create")
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
                if (willBeProjectManager != null && managerBelongs == null && !user.getRole().equals(Role.team_owner))
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
                    if (projectManagerParticipation != null) {
                        projectManagerParticipation.setFinalDate(LocalDate.now());
                        participationService.saveParticipation(projectManagerParticipation);
                        // Create a new participation but without privileges
                        Participation replacingParticipation = new Participation();
                        replacingParticipation.setUserTW(projectManagerParticipation.getUserTW());
                        replacingParticipation.setInitialDate(LocalDate.now());
                        replacingParticipation.setFinalDate(null);
                        replacingParticipation.setIsProjectManager(false);
                        replacingParticipation.setProject(project);
                        participationService.saveParticipation(replacingParticipation);

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

	@DeleteMapping(value = "/api/projects/participation/delete")
	public ResponseEntity<String> deleteParticipation(@RequestParam(required = true) Integer participationUserId,
			@RequestParam(required = true) Integer projectId, HttpServletRequest r) {
		try {
		    // Authority is accounted for in projectmanagerinterceptor
			UserTW user = userTWService.findUserById((Integer) r.getSession().getAttribute("userId"));
			Participation currentParticipation = participationService.findCurrentParticipation(participationUserId, projectId);
			if (currentParticipation != null)
            {
                // End the participation
                currentParticipation.setFinalDate(LocalDate.now());
                participationService.saveParticipation(currentParticipation);

                return ResponseEntity.ok().build();
            }
			else {
                return ResponseEntity.badRequest().build();
            }

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}
}
