package org.springframework.samples.petclinic.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TeamController {

	private final TeamService teamService;

	@Autowired
	public TeamController(TeamService teamService, UserTWService userService) {
		this.teamService = teamService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setAllowedFields("id");
	}

	@GetMapping(value = "/api/team")
	public ResponseEntity<String> getTeamName(HttpServletRequest req) {
		log.info("Obteniendo el nombre del team con id: " + req.getSession().getAttribute("teamId"));

		Integer teamId = (Integer) req.getSession().getAttribute("teamId");
		String teamName = teamService.findTeamById(teamId).getName();
		return ResponseEntity.ok(teamName);
	}

	@PostMapping(value = "/api/team")
	public ResponseEntity<String> updateTeam(HttpServletRequest req, @Valid @RequestBody Team teamAttrs) {
		try {
			log.info("Actualizando el team con id: " + req.getSession().getAttribute("teamId"));

			String name = teamAttrs.getName();
			Integer teamId = (Integer) req.getSession().getAttribute("teamId");
			Team team = teamService.findTeamById(teamId);
			if (name != null) {
				team.setName(name);
			} else {
				log.error("El team no existe");
				return ResponseEntity.badRequest().build();
			}
			log.info("Guardando el team");
			teamService.saveTeam(team);
			return ResponseEntity.ok("Team update");
		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping(value = "/api/team")
	public ResponseEntity<String> deleteTeam(HttpServletRequest req) {
		Integer teamId = (Integer) req.getSession().getAttribute("teamId");
		try {
			log.info("Eliminando el team con id: " + req.getSession().getAttribute("teamId"));
			teamService.deleteTeamById(teamId);
			return ResponseEntity.ok("Team deleted");
		} catch (DataAccessException d) {
			log.error("Error: " + d.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

}
