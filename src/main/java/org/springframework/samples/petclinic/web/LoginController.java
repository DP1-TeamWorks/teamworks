package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
	private final UserTWService userTWService;
	private final TeamService teamService;

	@Autowired
	public LoginController(TeamService teamService, UserTWService userTWService) {
		this.teamService = teamService;
		this.userTWService = userTWService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setAllowedFields("id");
	}

	@PostMapping(value = "/api/auth/login")
	public ResponseEntity<UserTW> login(HttpServletRequest r, @RequestBody Map<String, String> b) {
		try {
			String email = b.get("mail");
			String password = b.get("password");
			UserTW user = userTWService.getLoginUser(email, password);

			if (user != null) {
				r.getSession().setAttribute("userId", user.getId());
				r.getSession().setAttribute("teamId", user.getTeam().getId());

				return ResponseEntity.accepted().body(user);

			} else {
				return ResponseEntity.badRequest().build();
			}
		} catch (DataAccessException d) {
			return null;
		}
	}

	@GetMapping(value = "/api/auth/logout")
	public void logout(HttpServletRequest r) {
		// Unset the session
		r.getSession().invalidate();
	}

	@PostMapping(value = "/api/auth/signup")
	public ResponseEntity<String> signUp(@RequestBody Map<String, String> b) {
		try {
			// Set up the team
			String teamName = b.get("teamname");
			String identifier = b.get("identifier");
			Team team = new Team();
			team.setName(teamName);
			team.setIdentifier(identifier);

			// Set up the team_owner
			String name = b.get("username");
			String lastname = b.get("lastname");
			String password = b.get("password");
			UserTW user = new UserTW();
			user.setName(name);
			user.setLastname(lastname);
			user.setPassword(SecurityConfiguration.passwordEncoder().encode(password));
			System.out.println(user.getPassword());
			user.setEmail(user.getName().toLowerCase() + user.getLastname().toLowerCase() + "@" + team.getIdentifier());
			user.setRole(Role.team_owner);
			user.setTeam(team);

			// Save Team & Team_Owner
			teamService.saveTeam(team);
			userTWService.saveUser(user);

			return ResponseEntity.ok("Usuario y Team creado satisfactoriamente");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}

}
