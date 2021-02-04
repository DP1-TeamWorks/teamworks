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

	@Autowired
	public UserTWController(UserTWService userService, TeamService teamService, BelongsService belongsService,
			ParticipationService participationService) {
		this.userService = userService;
		this.teamService = teamService;
		this.participationService = participationService;
		this.belongsService = belongsService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/api/users")
	public Collection<UserTW.StrippedUser> getUsers(HttpServletRequest r) {
        Integer teamId = (Integer) r.getSession().getAttribute("teamId");
		List<UserTW.StrippedUser> l = userService.findUsersByTeam(teamId).stream().collect(Collectors.toList());
		return l;
	}

	@GetMapping(value = "/api/user")
	public Map<String, Object> getUser(HttpServletRequest r, Integer userId) {
		Map<String, Object> m = new HashMap<>();
		UserTW user = userService.findUserById(userId);
		m.put("user", user);
		List<Belongs> lb = belongsService.findUserBelongs(userId).stream().collect(Collectors.toList());
		m.put("currentDepartments", lb);
		List<Participation> lp = participationService.findUserParticipations(userId).stream()
				.collect(Collectors.toList());
		m.put("currentProjects", lp);
		return m;

	}

	@PostMapping(value = "/api/user")
	public ResponseEntity<String> postUser(HttpServletRequest r, @RequestBody UserTW user) {
		try {
			Integer teamId = (Integer) r.getSession().getAttribute("teamId");
			Team team = teamService.findTeamById(teamId);
			user.setTeam(team);
			user.setEmail(user.getName().toLowerCase() + user.getLastname().toLowerCase() + "@" + team.getIdentifier());
			user.setRole(Role.employee);
			user.setPassword(SecurityConfiguration.passwordEncoder().encode(user.getPassword()));
			userService.saveUser(user);
			return ResponseEntity.ok("User Created");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
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
	public Map<String, Object> getCredentials(HttpServletRequest r, Integer userId) {
		Map<String, Object> m = new HashMap<>();
		UserTW user = userService.findUserById(userId);
		m.put("isTeamManager", user.getRole().equals(Role.team_owner));
		List<Belongs> lb = belongsService.findCurrentUserBelongs(userId).stream().collect(Collectors.toList());
		m.put("currentDepartments", lb);
		List<Participation> lp = participationService.findCurrentParticipationsUser(userId).stream()
				.collect(Collectors.toList());
		m.put("currentProjects", lp);
		return m;
	}

}
