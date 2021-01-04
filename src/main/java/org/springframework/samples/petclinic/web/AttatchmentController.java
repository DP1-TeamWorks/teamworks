package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Attatchment;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.AttatchmentService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

public class AttatchmentController {
	
	private final AttatchmentService attatchmentService;
	private final MessageService messageService;
	
	
	@Autowired
	public AttatchmentController(AttatchmentService attatchmentService, MessageService messageService) {
		this.attatchmentService = attatchmentService;
		this.messageService = messageService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/*
	@GetMapping(value = "/api/attatchment")
	public List<Attatchment> getAttatchments(@RequestParam(required = false) String message) {
		if (message == null) {
			List<Attatchment> list = attatchmentService.getAllUsers().stream().collect(Collectors.toList());
			return list;
		} else {
			List<UserTW> list = userService.findUserByName(user).stream().collect(Collectors.toList());
			if (list == null)
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user");
			else {
				return list;
			}
		}
	}
	*/
	@PostMapping(value = "/api/attatchment")
	public ResponseEntity<String> postAttatchment(HttpServletRequest r, @RequestBody Message message) {
		try {
			Integer attatchmentId = (Integer) r.getSession().getAttribute("attatchmentId");
			Integer messageId = (Integer) r.getSession().getAttribute("messageId");
			//UserTW userAdmin=attatcgService.findUserById(messageId);
			if(userAdmin.getRole().equals(Role.team_owner)){
				Team team = teamService.findTeamById(teamId);
				user.setTeam(team);
				user.setRole(Role.employee);
				userService.saveUser(user);
				return ResponseEntity.ok("User Created");
			}else {
				return ResponseEntity.status(403).build();
			}
			

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping(value = "/api/attatchment")
	public ResponseEntity<String> deleteAttatchment(@RequestParam(required = true) Integer attatchmentId) {
		// System.out.println("Delete user: "+ userTWId);

		try {
			attatchmentService.deleteAttatchmentById(attatchmentId);
			return ResponseEntity.ok("Attatchment Deleted");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}

}
