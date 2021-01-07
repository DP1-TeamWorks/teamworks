package org.springframework.samples.petclinic.web;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
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
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserTWController {

	private final UserTWService userService;
	private final TeamService teamService;

	@Autowired
	public UserTWController(UserTWService userService, TeamService teamService) {
		this.userService = userService;
		this.teamService = teamService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/api/userTW")
	public List<UserTW> getUser(HttpServletRequest r) {
		List<UserTW> l = new ArrayList<>();
		Integer teamId = (Integer) r.getSession().getAttribute("teamId");
		l = teamService.findTeamById(teamId).getUsers();
		return l;
	}

	@PostMapping(value = "/api/userTW")
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

	@DeleteMapping(value = "/api/userTW")
	public ResponseEntity<String> deleteUser(@RequestParam(required = true) Integer userId) {
		try {
			userService.deleteUserById(userId);
			return ResponseEntity.ok("User Deleted");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}
	
}
