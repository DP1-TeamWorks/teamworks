package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.coyote.Response;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Collection<Belongs>> getBelongsFromDepartment(@RequestParam(required = true) Integer departmentId, HttpServletRequest r)
    {
        try
        {
            Collection<Belongs> belongs = belongsService.findCurrentBelongsInDepartment(departmentId);
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
			//Boolean isTeamOwner = user.getRole().equals(Role.team_owner);
			if (currentBelongs == null) {
				Department department = departmentService.findDepartmentById(departmentId);
				UserTW belongUser = userTWService.findUserById(belongUserId);
				Belongs belongs = new Belongs();
				belongs.setDepartment(department);
				belongs.setUserTW(belongUser);
				belongs.setIsDepartmentManager(false);

				if (isDepartmentManager != null && isDepartmentManager)
				{
				    Department dp = departmentService.findDepartmentById(departmentId);
				    belongs.setIsDepartmentManager(true);
                    Belongs departmentManagerBelongs = belongsService.findCurrentDepartmentManager(departmentId);
                    if (departmentManagerBelongs != null)
                    {
                        // they're department manager. since there can only be one, they will lose privileges
                        departmentManagerBelongs.setFinalDate(LocalDate.now());
                        belongsService.saveBelongs(departmentManagerBelongs);
                    }
				}
				belongsService.saveBelongs(belongs);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.badRequest().body("alreadyexists");
			}

		} catch (DataAccessException d) {
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

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}
}
