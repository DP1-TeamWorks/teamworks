package org.springframework.samples.petclinic.web;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.ManyTeamOwnerException;
import org.springframework.samples.petclinic.validation.UserValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserTWController {

	private final UserTWService userService;
	private final TeamService teamService;
	private final BelongsService belongsService;
	private final ParticipationService participationService;

	private final UserValidator userValidator;

	@Autowired
	public UserTWController(UserTWService userService, TeamService teamService, BelongsService belongsService,
			ParticipationService participationService, UserValidator userValidator) {
		this.userService = userService;
		this.teamService = teamService;
		this.participationService = participationService;
		this.belongsService = belongsService;
		this.userValidator = userValidator;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/api/users")
	public Collection<UserTW> getUsers(HttpServletRequest r) {
        Integer teamId = (Integer) r.getSession().getAttribute("teamId");
		List<UserTW> l = userService.findUsersByTeam(teamId).stream().collect(Collectors.toList());
		return l;
	}

	@GetMapping(value = "/api/user")
	public ResponseEntity<Map<String, Object>> getUser(HttpServletRequest r, Integer userId) {
		Integer teamId =(Integer) r.getSession().getAttribute("teamId");
		UserTW user = userService.findUserById(userId);
		Map<String, Object> m = new HashMap<>();
		if(user!=null&&user.getTeam().getId().equals(teamId)) {
			m.put("user", user);
			List<Belongs> lb = belongsService.findUserBelongs(userId).stream().collect(Collectors.toList());
			m.put("currentDepartments", lb);
			List<Participation> lp = participationService.findUserParticipations(userId).stream()
					.collect(Collectors.toList());
			m.put("currentProjects", lp);
			return ResponseEntity.ok(m);
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping(value = "/api/user")
	public ResponseEntity<String> postUser(HttpServletRequest r, @RequestBody UserTW user, BindingResult errors) {
		try {

			userValidator.validate(user, errors);
			if (!errors.hasErrors()) {
				Integer teamId = (Integer) r.getSession().getAttribute("teamId");
				Team team = teamService.findTeamById(teamId);
				user.setTeam(team);
				user.setEmail(
						user.getName().toLowerCase() + user.getLastname().toLowerCase() + "@" + team.getIdentifier());
				user.setPassword(SecurityConfiguration.passwordEncoder().encode(user.getPassword()));
				userService.saveUser(user);
				return ResponseEntity.ok("User Created");
			} else {
				return ResponseEntity.badRequest().body(errors.getAllErrors().toString());
			}

		} catch (DataAccessException | ManyTeamOwnerException d) {
			return ResponseEntity.badRequest().body(d.getMessage());
		}
	}

	@DeleteMapping(value = "/api/user")
	public ResponseEntity<String> deleteUser(@RequestParam(required = true) Integer userId) {
		try {
			userService.deleteUserById(userId);
			return ResponseEntity.ok("User Deleted");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}

	@GetMapping(value = "/api/user/credentials")
	public ResponseEntity<Map<String, Object>> getCredentials(HttpServletRequest r, Integer userId) {
		Integer teamId =(Integer) r.getSession().getAttribute("teamId");
		Map<String, Object> m = new HashMap<>();
		UserTW user = userService.findUserById(userId);
		if(user!=null&&user.getTeam().getId().equals(teamId)) {

			m.put("isTeamManager", user.getRole().equals(Role.team_owner));
			List<Belongs> lb = belongsService.findCurrentUserBelongs(userId).stream().collect(Collectors.toList());
			m.put("currentDepartments", lb);
			List<Participation> lp = participationService.findCurrentParticipationsUser(userId).stream()
				.collect(Collectors.toList());
			m.put("currentProjects", lp);
			return ResponseEntity.ok(m);
		}else {
			return ResponseEntity.badRequest().build();
		}
	}

}
