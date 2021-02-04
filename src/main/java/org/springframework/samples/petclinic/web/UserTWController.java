package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.samples.petclinic.validation.IdParentIncoherenceException;
import org.springframework.samples.petclinic.validation.ManyTeamOwnerException;
import org.springframework.samples.petclinic.validation.UserValidator;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	@GetMapping(value = "/api/usersTW")
	public List<UserTW> getUsers(HttpServletRequest r) {
		List<UserTW> l = new ArrayList<>();

		Integer teamId = (Integer) r.getSession().getAttribute("teamId");
		log.info("Obteniendo usuarios del team con id: " + teamId);
		l = teamService.findTeamById(teamId).getUsers();
		return l;
	}

	@GetMapping(value = "/api/userTW")
	public ResponseEntity<Map<String, Object>> getUser(HttpServletRequest r, Integer userId) {
		try {
			Integer teamId = (Integer) r.getSession().getAttribute("teamId");

			UserTW user = userService.findUserById(userId);
			Map<String, Object> m = new HashMap<>();
			log.info("Comprobando si el usuario pertenece al team con id:" + teamId);
			if (user.getTeam().getId().equals(teamId)) {
				log.info("Montando json de informacion de usuario con id:" + userId);
				m.put("user", user);
				log.info("Añadiendo información de belongs del usuario");
				List<Belongs> lb = belongsService.findUserBelongs(userId).stream().collect(Collectors.toList());
				m.put("currentDepartments", lb);
				log.info("Añadiendo información de participations del usuario");
				List<Participation> lp = participationService.findUserParticipations(userId).stream()
						.collect(Collectors.toList());
				m.put("currentProjects", lp);
				log.info("Json generado correctamente");
				return ResponseEntity.ok(m);
			} else {
				throw new IdParentIncoherenceException("Team", "User");
			}
		} catch (DataAccessException | IdParentIncoherenceException d) {
			log.error("Error:" + d.getMessage());
			return ResponseEntity.badRequest().build();
		}

	}

	@PostMapping(value = "/api/userTW")
	public ResponseEntity<String> postUser(HttpServletRequest r, @RequestBody UserTW user, BindingResult errors) {
		try {
			log.info("Validando usuario");
			userValidator.validate(user, errors);
			if (!errors.hasErrors()) {
				log.info("Validación superada, guardando user");
				Integer teamId = (Integer) r.getSession().getAttribute("teamId");
				Team team = teamService.findTeamById(teamId);
				log.info("Añadiendo usuario a team con id: " + teamId);
				user.setTeam(team);
				log.info("Generando email");
				user.setEmail(
						user.getName().toLowerCase() + user.getLastname().toLowerCase() + "@" + team.getIdentifier());
				log.info("Encriptando contraseña");
				user.setPassword(SecurityConfiguration.passwordEncoder().encode(user.getPassword()));
				log.info("Contraseña encriptada correctamenete, guardando usurio");
				userService.saveUser(user);
				log.info("Usuario gurdado correctamente");
				return ResponseEntity.ok("User Created");
			} else {
				log.error("Se han detectado errores de validacion " + errors.getAllErrors().toString());
				return ResponseEntity.badRequest().body(errors.getAllErrors().toString());
			}

		} catch (DataAccessException | ManyTeamOwnerException d) {
			log.error("Error: " + d.getMessage());
			return ResponseEntity.badRequest().body(d.getMessage());
		}
	}

	@DeleteMapping(value = "/api/userTW")
	public ResponseEntity<String> deleteUser(HttpServletRequest r,@RequestParam(required = true) Integer userId) {
		try {
			UserTW user=userService.findUserById(userId);
			Integer teamId=(Integer)r.getSession().getAttribute("teamId");
			log.info("Comprobando que el usuario pertenece al team con id: "+teamId);
			if(user.getTeam().getId().equals(teamId)) {
				log.info("Borrando usuario con id:" + userId);
				userService.deleteUserById(userId);
				log.info("Usuario borrado correctamente");
				return ResponseEntity.ok("User Deleted");
			}else {
				throw new IdParentIncoherenceException("Team", "User");
			}
			

		} catch (DataAccessException | IdParentIncoherenceException d) {
			log.error("Error: " + d.getMessage());
			return ResponseEntity.badRequest().build();
		}

	}

	@GetMapping(value = "/api/userTW/credentials")
	public ResponseEntity<Map<String, Object>> getCredentials(HttpServletRequest r, Integer userId) {
		try {
			Integer teamId = (Integer) r.getSession().getAttribute("teamId");
			Map<String, Object> m = new HashMap<>();
			UserTW user = userService.findUserById(userId);
			log.info("Comprobando si el usuario con id " + userId + " pertenece al team con id: " + teamId);
			if (user.getTeam().getId().equals(teamId)) {
				log.info("Generando json con informacion de privilegios");
				m.put("isTeamManager", user.getRole().equals(Role.team_owner));
				List<Belongs> lb = belongsService.findCurrentUserBelongs(userId).stream().collect(Collectors.toList());
				m.put("currentDepartments", lb);
				List<Participation> lp = participationService.findCurrentParticipationsUser(userId).stream()
						.collect(Collectors.toList());
				m.put("currentProjects", lp);
				log.info("Json generado correctamente");
				return ResponseEntity.ok(m);
			} else {

				throw new IdParentIncoherenceException("Team", "User");
			}
		}

		catch (DataAccessException | IdParentIncoherenceException d) {
			log.error("Error: " + d.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

}
