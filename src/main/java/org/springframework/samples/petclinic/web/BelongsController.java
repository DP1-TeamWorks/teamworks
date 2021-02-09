package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.enums.Role;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.DateIncoherenceException;
import org.springframework.samples.petclinic.validation.IdParentIncoherenceException;
import org.springframework.samples.petclinic.validation.ManyDepartmentManagerException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value="/api/departments/belongs")
    public ResponseEntity<List<Belongs>> getBelongsFromDepartment(@RequestParam(required = true) Integer departmentId, HttpServletRequest r)
    {
        try
        {
            List<Belongs> belongs = belongsService.findCurrentBelongsInDepartment(departmentId).stream().sorted(Comparator.comparing(Belongs::getLastname).thenComparing(Belongs::getName)).collect(Collectors.toList());
            return ResponseEntity.ok(belongs);
        } catch (DataAccessException e)
        {
            return ResponseEntity.badRequest().build();
        }
    }


	@PostMapping(value = "/api/departments/belongs")
	public ResponseEntity<String> createBelongs(@RequestParam(required = true) Integer belongUserId,
			@RequestParam(required = true) Integer departmentId,
			@RequestParam(required = false) Boolean isDepartmentManager, HttpServletRequest r) {

		try {

			Belongs currentBelongs = belongsService.findCurrentBelongs(belongUserId, departmentId);
			UserTW user = userTWService.findUserById((Integer) r.getSession().getAttribute("userId"));
			Boolean isTeamOwner = user.getRole().equals(Role.team_owner);
			Department department = departmentService.findDepartmentById(departmentId);
			UserTW belonguser = userTWService.findUserById(belongUserId);

			if(!belonguser.getTeam().equals(user.getTeam())) {
				throw new IdParentIncoherenceException("Team", "User");
			}

//			if(!belonguser.getTeam().equals(department.getTeam())) {
//				throw new IdParentIncoherenceException("Team", "Department");
//			}

            // Check if user DOESNT exist in department OR is being promoted/demoted
			if (currentBelongs == null || (isDepartmentManager != null && currentBelongs.getIsDepartmentManager() != isDepartmentManager)) {
				UserTW belongUser = userTWService.findUserById(belongUserId);
				Belongs belongs = new Belongs();
				belongs.setDepartment(department);
				belongs.setUserTW(belongUser);
				belongs.setIsDepartmentManager(false);
				if (currentBelongs != null)
                {
                    // End the the previous belongs
                    currentBelongs.setFinalDate(LocalDate.now());
                    belongsService.saveBelongs(currentBelongs);
                }

				if (isDepartmentManager != null && isDepartmentManager)
				{
				    log.info("User is being promoted / demoted");
				    Department dp = departmentService.findDepartmentById(departmentId);
				    belongs.setIsDepartmentManager(true);
                    Belongs departmentManagerBelongs = belongsService.findCurrentDepartmentManager(departmentId);
                    if (departmentManagerBelongs != null)
                    {
                        // they're department manager. since there can only be one, they will lose privileges
                        departmentManagerBelongs.setFinalDate(LocalDate.now());
                        belongsService.saveBelongs(departmentManagerBelongs);
                        // Create a new belong but without privileges
                        Belongs replacingBelongs = new Belongs();
                        replacingBelongs.setUserTW(departmentManagerBelongs.getUserTW());
                        replacingBelongs.setInitialDate(LocalDate.now());
                        replacingBelongs.setFinalDate(null);
                        replacingBelongs.setIsDepartmentManager(false);
                        replacingBelongs.setDepartment(department);
                        belongsService.saveBelongs(replacingBelongs);
                    }
				}
				belongsService.saveBelongs(belongs);
				return ResponseEntity.ok().build();
			} else {
			    log.error("Tried to create a belongs for an already existing member");
			    // there's already a belongs for that user
				return ResponseEntity.badRequest().body("alreadyexists");
			}

		} catch (DataAccessException | ManyDepartmentManagerException | DateIncoherenceException | IdParentIncoherenceException d) {
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
			// Authority is sorted out in the interceptor so we only do minimal checks here
			if (belongs != null)
            {
                belongs.setFinalDate(LocalDate.now());
                belongsService.saveBelongs(belongs);
                return ResponseEntity.ok().build();
            } else
            {
                return ResponseEntity.badRequest().build();
            }

		} catch (DataAccessException | ManyDepartmentManagerException | DateIncoherenceException d) {
			return ResponseEntity.badRequest().build();
		}
	}
}
