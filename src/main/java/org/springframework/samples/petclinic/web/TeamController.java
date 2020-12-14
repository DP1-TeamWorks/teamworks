package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.TeamService;
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
	
	@Autowired
	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setAllowedFields("id");
	}
	
	@GetMapping(value= "/teams")
	public List<Team> getTeams(@RequestParam (required=false) String name) {
		List<Team> list = new ArrayList<>();
		if(name==null) {
			list= teamService.getAllTeams().stream().collect(Collectors.toList());
		} else {
			list = teamService.findTeamByName(name).stream().collect(Collectors.toList());
		}
		return list;
		
	}
	
	
	@PostMapping(value = "/teams")
	public ResponseEntity<String> postTeams(@RequestBody Team team) {
		try {
			teamService.saveTeam(team);
			return ResponseEntity.ok("Team created");
			
		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	
	@DeleteMapping(value = "/teams")
	public ResponseEntity<String> deleteTeams(@RequestParam(required = true) Integer teamId) {
		try {
			teamService.deleteTeamById(teamId);
			return ResponseEntity.ok("Team deleted");
		} catch (DataAccessException d) {
			return ResponseEntity.notFound().build();
		}
	}

}
