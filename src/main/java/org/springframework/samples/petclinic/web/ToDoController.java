package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.service.MilestoneService;
import org.springframework.samples.petclinic.service.TagService;
import org.springframework.samples.petclinic.service.ToDoService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.IdParentIncoherenceException;
import org.springframework.samples.petclinic.validation.ToDoMaxToDosPerAssigneeException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class ToDoController {
	private final ToDoService toDoService;
	private final UserTWService userService;
	private final MilestoneService milestoneService;
	private final TagService tagService;

	@Autowired
	public ToDoController(ToDoService toDoService, UserTWService userService, MilestoneService milestoneService, TagService tagService) {
		this.toDoService = toDoService;
		this.userService = userService;
		this.milestoneService = milestoneService;
		this.tagService = tagService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/api/toDos/mine")
	public List<ToDo> getMyToDos(@RequestParam(required = true) Integer milestoneId, HttpServletRequest r) {
		log.info("Obteniendo los toDos del user con id: " + r.getSession().getAttribute("userId"));
		Integer userId = (Integer) r.getSession().getAttribute("userId");
		return toDoService.findToDoByMilestoneAndUser(milestoneId, userId).stream().collect(Collectors.toList());
	}

	@GetMapping(value = "/api/toDos/mine/all")
	public Map<String, List<ToDo>> getAllMyToDosByProject(HttpServletRequest r) {
		Integer userId = (Integer) r.getSession().getAttribute("userId");
		log.info("Obteniendo todos los ToDos por proyecto del usuario con id:"+userId);
		return toDoService.findToDoByUser(userId).stream()
				.collect(Collectors.groupingBy(todo -> todo.getMilestone().getProject().getName()));
	}

    @GetMapping(value = "/api/toDos")
    public Collection<ToDo> getTodosFromMilestone(HttpServletRequest r, @RequestParam(required = true) Integer milestoneId) {
	    log.info("Obteniendo ToDos de la milestone con id:"+milestoneId);
    	return toDoService.findToDosByMilestone(milestoneId);
    }

	@PostMapping(value = "/api/toDos/mine/create")
	public ResponseEntity<ToDo> createPersonalToDo(HttpServletRequest r, @Valid @RequestBody ToDo toDo,
			@RequestParam(required = true) Integer milestoneId) {
		log.info("Creando un toDo para el user con id: " + r.getSession().getAttribute("userId")
				+ " en la milestone con id: " + milestoneId);
		Integer userId = (Integer) r.getSession().getAttribute("userId");
		return createToDo(toDo, r, milestoneId, userId, true);
	}

	@PostMapping(value = "/api/toDos/create")
	public ResponseEntity<ToDo> createToDo(@Valid @RequestBody ToDo toDo, HttpServletRequest r,
			@RequestParam(required = true) Integer milestoneId, @RequestParam(required = false) Integer userId,
			Boolean mine) {
		try {
			log.info("Creando un toDo para el user con id: " + userId + " en la milestone con id: " + milestoneId);
            Milestone milestone = milestoneService.findMilestoneById(milestoneId);
            UserTW user = null;
			if (userId != null)
            {
                user = userService.findUserById(userId);

                if (!mine && user.getParticipation().stream().map(Participation::getProject)
                    .anyMatch(p -> p.equals(milestone.getProject())))
                    throw new IdParentIncoherenceException("Project", "User");
            }
			
			toDo.setAssignee(user);
			toDo.setMilestone(milestone);
			toDo.setDone(false);
			log.info("Guardando el todo");
			toDoService.saveToDo(toDo);
			log.info("ToDo guradado correctamente");
			return ResponseEntity.ok().body(toDo);

		} catch (DataAccessException | ToDoMaxToDosPerAssigneeException | IdParentIncoherenceException d) {
            log.error("Error",d);
			return ResponseEntity.badRequest().build();
		}
	}

	// parameter milestoneId could be removed because we can infer it from ToDo
    // however the projectmanagerinterceptor needs it so we'll keep it
    @PostMapping(value = "/api/toDos/assign")
    public ResponseEntity<String> assignTodo(@RequestParam(required = true) Integer todoId, @RequestParam(required = true) Integer milestoneId,
                                             HttpServletRequest r, @RequestParam(required = true) Integer assigneeId) {
        try {
        	log.info("El usuario con id: " + r.getSession().getAttribute("userId") + " esta marcando el todo con id: "
					+ todoId + " como completado");
            UserTW user = userService.findUserById(assigneeId);
            ToDo todo = toDoService.findToDoById(todoId);

            Milestone milestone = milestoneService.findMilestoneById(milestoneId);
            
            if (assigneeId == -1)
            {
                // unassign
                todo.setAssignee(null);
                toDoService.saveToDo(todo);
                return ResponseEntity.ok().build();
            }
            // Check assignee is from project
            log.info("Comprobando si el asignado pertenece al proyecto");
            if (!user.getParticipation().stream().map(Participation::getProject)
                .anyMatch(p -> p.equals(milestone.getProject())))
                throw new IdParentIncoherenceException("Project", "User");

            todo.setAssignee(user);
            log.info("Guardando el todo");
            toDoService.saveToDo(todo);
            log.info("ToDo guradado correctamente");
            return ResponseEntity.ok().body(user.getName() + " " + user.getLastname());

        } catch (DataAccessException | ToDoMaxToDosPerAssigneeException | IdParentIncoherenceException d) {
            log.error("Error",d);
        	if (d.getClass().equals(ToDoMaxToDosPerAssigneeException.class))
            {
                return ResponseEntity.badRequest().body("toomany");
            } else
            {
                return ResponseEntity.badRequest().body(d.getMessage());
            }
        }
    }

    // milestoneId is not needed but projectmanagerinterceptor needs it
    // this should be much better documented (i.e swagger)
    // ---
    // the reason it takes both todoId and to-do body (instead of string) is because of frontend EditableField.
    // it sends a body with the relevant fields
    @PostMapping(value = "/api/toDos/updateTitle")
    public ResponseEntity<String> updateTitle(@RequestParam(required = true) Integer todoId, @RequestParam(required = true) Integer milestoneId,
                                             HttpServletRequest r, @RequestBody ToDo todoRequest) {
        try {
        	log.info("Validando ToDo tittle");
            // we validate here since we didnt mark as @Valid
            if (todoRequest.getTitle() == null || todoRequest.getTitle().length() == 0 || todoRequest.getTitle().length() > 35)
            {
            	log.error("Error al validar el titulo");
                return ResponseEntity.badRequest().build();
            }
            
            ToDo todo = toDoService.findToDoById(todoRequest.getId());
            todo.setTitle(todoRequest.getTitle());
            log.info("Actualizando titulo del ToDo");
            toDoService.saveToDo(todo);
            log.info("ToDo actualizado correctamente");
            return ResponseEntity.ok().build();

        } catch (DataAccessException | ToDoMaxToDosPerAssigneeException d) {
        	log.error("Error",d);
            return ResponseEntity.badRequest().body(d.getMessage());
        }
    }

    // parameter milestoneId could be removed because we can infer it from ToDo
    // however the projectmanagerinterceptor needs it so we'll keep it
    @PostMapping(value = "/api/toDos/setTags")
    public ResponseEntity<ToDo> setTags(@RequestParam(required = true) Integer todoId, @RequestParam(required = true) Integer milestoneId,
                                             HttpServletRequest r, @RequestBody(required = true) List<Integer> tagIds) {
        try {
        	log.info("Añadiendo tags al ToDo con id:"+todoId);
            ToDo todo = toDoService.findToDoById(todoId);
            Milestone milestone = milestoneService.findMilestoneById(milestoneId);

            List<Tag> tags = new ArrayList<>();
            // Check tags are from project
            log.info("Comprobando que las tags pertenecen al proyecto");
            for (Integer tagId : tagIds)
            {
                Tag t = tagService.findTagById(tagId);
                if (!t.getProject().equals(milestone.getProject()))
                    throw new IdParentIncoherenceException("Tag","Project");
                tags.add(t);
            }

            todo.setTags(tags);
            toDoService.saveToDo(todo);
            log.info("Tags añadidas al ToDo correctamente");
            return ResponseEntity.ok().body(todo);

        } catch (DataAccessException | ToDoMaxToDosPerAssigneeException | IdParentIncoherenceException d) {
            log.error("Error",d);
        	return ResponseEntity.badRequest().build();
        }
    }

	@PostMapping(value = "/api/toDos/markAsDone")
	public ResponseEntity<String> markAsDone(HttpServletRequest r,
                        @RequestParam(required = true) int toDoId,
                         @RequestParam(value="unmark", required = false, defaultValue = "true") Boolean unmark) {
		try {
			log.info("El usuario con id: " + r.getSession().getAttribute("userId") + " esta marcando el todo con id: "
					+ toDoId + " como completado");
			ToDo toDo = toDoService.findToDoById(toDoId);
			if (unmark != null && unmark)
            {
                toDo.setDone(false);
            } else
            {
                toDo.setDone(true);
            }
			log.info("Guardando el todo");
			toDoService.saveToDo(toDo);
			log.info("ToDo guardado correctamente");
			return ResponseEntity.ok().build();

		} catch (DataAccessException | ToDoMaxToDosPerAssigneeException d) {
			log.error("Error",d);
			return ResponseEntity.badRequest().body(d.getMessage());
		}
	}

	// we keep milestoneid because of projectmanagerinterceptor
    @DeleteMapping(value = "/api/toDos/delete")
    public ResponseEntity<String> deleteTodo(HttpServletRequest r, @RequestParam(required = true) int toDoId, @RequestParam(required = true) Integer milestoneId) {
        try {
        	log.info("Eliminando ToDo");
            toDoService.deleteToDoById(toDoId);
            log.info("ToDo eliminado con exito");
            return ResponseEntity.ok().build();
        } catch (DataAccessException d) {
        	log.error("Error",d);
            return ResponseEntity.badRequest().body(d.getMessage());
        }
    }
}
