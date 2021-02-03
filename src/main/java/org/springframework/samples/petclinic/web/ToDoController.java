package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Milestone;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.MilestoneService;
import org.springframework.samples.petclinic.service.ToDoService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.ToDoLimitMilestoneException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToDoController {
	private final ToDoService toDoService;
	private final UserTWService userService;
	private final MilestoneService milestoneService;

	@Autowired
	public ToDoController(ToDoService toDoService, UserTWService userService, MilestoneService milestoneService) {
		this.toDoService = toDoService;
		this.userService = userService;
		this.milestoneService = milestoneService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/api/toDos/mine")
	public List<ToDo> getMyToDos(@RequestParam(required = true) Integer milestoneId, HttpServletRequest r) {
		Integer userId = (Integer) r.getSession().getAttribute("userId");
		return toDoService.findToDoByMilestoneAndUser(milestoneId, userId).stream().collect(Collectors.toList());
	}

	@PostMapping(value = "/api/toDos/mine")
	public ResponseEntity<String> createPersonalToDo(@Valid @RequestBody ToDo toDo, HttpServletRequest r,
			@RequestParam(required = true) Integer milestoneId) {

		Integer userId = (Integer) r.getSession().getAttribute("userId");
		return createToDo(toDo, r, milestoneId, userId);

	}

	@PostMapping(value = "/api/toDos")
	public ResponseEntity<String> createToDo(@Valid @RequestBody ToDo toDo, HttpServletRequest r,
			@RequestParam(required = true) Integer milestoneId, @RequestParam(required = false) Integer userId) {
		try {
			UserTW user = userService.findUserById(userId);
			Milestone milestone = milestoneService.findMilestoneById(milestoneId);
			toDo.setAssignee(user);
			toDo.setMilestone(milestone);
			toDo.setDone(false);
			toDoService.saveToDo(toDo);
			return ResponseEntity.ok().build();

		} catch (DataAccessException | ToDoLimitMilestoneException d) {
			return ResponseEntity.badRequest().body(d.getMessage());
		}
	}

	@PostMapping(value = "/api/toDos/markAsDone")
	public ResponseEntity<String> markAsDone(HttpServletRequest r, @RequestParam(required = true) int toDoId) {
		try {
			ToDo toDo = toDoService.findToDoById(toDoId);
			toDo.setDone(true);
			toDoService.saveToDo(toDo);
			return ResponseEntity.ok().build();

		} catch (DataAccessException | ToDoLimitMilestoneException d) {
			return ResponseEntity.badRequest().body(d.getMessage());
		}
	}
}
