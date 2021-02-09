package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.enums.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.ManyTeamOwnerException;
import org.springframework.samples.petclinic.validation.TeamValidator;
import org.springframework.samples.petclinic.validation.UserValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
	private final UserTWService userTWService;
	private final TeamService teamService;
	private final UserValidator userValidator;
	private final TeamValidator teamValidator;

	@Autowired
	public AuthController(TeamService teamService, UserTWService userTWService, UserValidator userValidator,
			TeamValidator teamValidator) {
		this.teamService = teamService;
		this.userTWService = userTWService;
		this.userValidator = userValidator;
		this.teamValidator = teamValidator;
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

				return ResponseEntity.accepted().build();

			} else {
				return ResponseEntity.badRequest().build();
			}
		} catch (DataAccessException d) {
			return  ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping(value = "/api/auth/logout")
	public void logout(HttpServletRequest r) {
		// Unset the session
		r.getSession().invalidate();
	}

	@PostMapping(value = "/api/auth/signup")
	public ResponseEntity<String> signUp(@RequestBody Map<String, String> b, BindingResult errors) {
		try {
			// Set up the team
			String teamName = b.get("teamname");
			String identifier = b.get("identifier");
			Team team = new Team();
			team.setName(teamName);
			team.setIdentifier(identifier);
			teamValidator.validate(team, errors);
			Boolean teamHasErrors = errors.hasErrors();

			// Set up the team_owner
			String name = b.get("username");
			String lastname = b.get("lastname");
			String password = b.get("password");
			UserTW user = new UserTW();
			user.setName(name);
			user.setLastname(lastname);
			user.setEmail(user.getName().toLowerCase() + user.getLastname().toLowerCase() + "@" + team.getIdentifier());
			user.setRole(Role.team_owner);
			user.setTeam(team);
			user.setPassword(password);
			userValidator.validate(user, errors);
			Boolean userHasErrors = errors.hasErrors();
			user.setPassword(SecurityConfiguration.passwordEncoder().encode(password));
			if (teamHasErrors || userHasErrors) {
				return ResponseEntity.badRequest().body(errors.getAllErrors().toString());
			} else {
				// Save Team & Team_Owner
				teamService.saveTeam(team);
				userTWService.saveUser(user);
				return ResponseEntity.ok("Usuario y Team creado satisfactoriamente");
			}

		} catch (DataAccessException | ManyTeamOwnerException d) {
			return ResponseEntity.badRequest().body(d.getMessage());
		}

	}

}
