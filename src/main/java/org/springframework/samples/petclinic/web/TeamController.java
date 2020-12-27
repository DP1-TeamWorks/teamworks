package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
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

@RestController
public class TeamController {

	private final TeamService teamService;
	private final UserTWService userService;

	@Autowired
	public TeamController(TeamService teamService, UserTWService userService) {
		this.teamService = teamService;
		this.userService = userService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setAllowedFields("id");
	}

	@GetMapping(value = "/api/teams")
	public List<Team> getTeams(@RequestParam(required = false) String name) {
		List<Team> list = new ArrayList<>();
		if (name == null) {
			list = teamService.getAllTeams().stream().collect(Collectors.toList());
		} else {
			list = teamService.findTeamByName(name).stream().collect(Collectors.toList());
		}
		return list;

	}

	@PostMapping(value = "/api/teams")
	public ResponseEntity<String> postTeams(@RequestBody Team team) {
		try {
			teamService.saveTeam(team);
			return ResponseEntity.ok("Team created");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping(value = "/api/teams/update")
	public ResponseEntity<String> updateTeams(HttpServletRequest r, @RequestParam String name,
			@RequestParam String identifier) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			Integer teamId = (Integer) r.getSession().getAttribute("teamId");
			UserTW user = userService.findUserById(userId);
			if (user.getRole().equals(Role.team_owner)) {
				Team team = teamService.findTeamById(teamId);
				if (name != null && identifier != null) {
					team.setName(name);
					team.setIdentifier(identifier);
				} else if (name != null) {
					team.setName(name);
				} else if (identifier != null) {
					team.setIdentifier(identifier);
				} else {
					return ResponseEntity.badRequest().build();
				}
				teamService.saveTeam(team);
				return ResponseEntity.ok("Team update");
			} else {
				return ResponseEntity.status(403).build();
			}

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping(value = "/api/teams")
	public ResponseEntity<String> deleteTeams(@RequestParam(required = true) Integer teamId) {
		try {
			teamService.deleteTeamById(teamId);
			return ResponseEntity.ok("Team deleted");
		} catch (DataAccessException d) {
			return ResponseEntity.notFound().build();
		}
	}

}
