package org.springframework.samples.petclinic.web;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.enums.Role;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.ManyTeamOwnerException;
import org.springframework.samples.petclinic.validation.UserValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping(value = "/api/users")
	public Collection<UserTW.StrippedUser> getUsers(HttpServletRequest r) {
        Integer teamId = (Integer) r.getSession().getAttribute("teamId");
        log.info("Obteniendo usuarios del team con id: " + teamId);
        List<UserTW.StrippedUser> l = userService.findUsersByTeam(teamId).stream().map(x -> new StrippedUserImpl(x)).collect(Collectors.toList());
        return l;
	}

	@GetMapping(value = "/api/user")
	public ResponseEntity<Map<String, Object>> getUser(HttpServletRequest r, @RequestParam("userId") Integer userId) {
		Integer teamId =(Integer) r.getSession().getAttribute("teamId");
		UserTW user = userService.findUserById(userId);
		Map<String, Object> m = new HashMap<>();
		log.info("Comprobando si el usuario pertenece al team con id:" + teamId);
		if(user!=null&&user.getTeam().getId().equals(teamId)) {
			log.info("Montando json de informacion de usuario con id:" + userId);
			m.put("user", new StrippedUserImpl(user));
			log.info("Añadiendo información de belongs del usuario");
			List<Belongs> lb = belongsService.findUserBelongs(userId).stream().collect(Collectors.toList());
			m.put("departmentBelongs", lb);
			log.info("Añadiendo información de participations del usuario");
			List<Participation> lp = participationService.findUserParticipations(userId).stream()
					.collect(Collectors.toList());
			m.put("projectParticipations", lp);
			log.info("Json generado correctamente");
			return ResponseEntity.ok(m);
		}
		else {
			log.error("Error el usuario no existe o no pertenece al departamento");
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping(value = "/api/user/create")
	public ResponseEntity<String> postUser(HttpServletRequest r, @RequestBody UserTW user, BindingResult errors) {
		try {
			log.info("Validando usuario");
			userValidator.validate(user, errors);
			if (!errors.hasErrors()) {
				log.info("Validación superada, guardando user");
				Integer teamId = (Integer) r.getSession().getAttribute("teamId");
				log.info("Añadiendo usuario a team con id: " + teamId);
				Team team = teamService.findTeamById(teamId);
				user.setTeam(team);
				user.setRole(Role.employee);
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
				return ResponseEntity.badRequest().body("errors");
			}

		} catch (DataAccessException | ManyTeamOwnerException d) {
			log.error("Error:",d);
			return ResponseEntity.badRequest().body("alreadyexists");
		}
	}

    @PostMapping(value = "/api/user/update")
    public ResponseEntity<String> updateUser(HttpServletRequest r, @RequestBody UserTW user, BindingResult errors) {
        try {
            UserTW dbUser = userService.findUserById(user.getId());
            log.error("Comprobando si el usuario existe");
            if (dbUser == null) {
            	log.error("No existe el usuario");
            	return ResponseEntity.badRequest().build();
            }
                
            log.info("Validando usuario");
            userValidator.validate(user, errors);
            
            if (user.getName() != null)
            {
                if (errors.hasFieldErrors("name")) {
                	log.error("Errores de validacion:",errors.getAllErrors().toString());
                	return ResponseEntity.badRequest().build();
                }
                    
                dbUser.setName(user.getName());
            }

            if (user.getLastname() != null)
            {
                if (errors.hasFieldErrors("Lastname")) {
                	log.error("Errores de validacion:",errors.getAllErrors().toString());
                	return ResponseEntity.badRequest().build();
                }
                   
                dbUser.setLastname(user.getLastname());
            }

            if (user.getPassword() != null)
            {
                if (errors.hasFieldErrors("Password")) {
                	log.error("Errores de validacion:",errors.getAllErrors().toString());
                	return ResponseEntity.badRequest().build();
                }
                    
                dbUser.setPassword(user.getPassword());
            }
            
            if (user.getRole() != null && !user.getRole().equals(dbUser.getRole()))
            {
                if (user.getRole().equals(Role.team_owner))
                {
                    // if there's another team owner, remove their privileges
                    UserTW teamOwner = userService.findTeamOwner(dbUser.getTeam().getId());
                    if (!teamOwner.equals(dbUser))
                    {
                        teamOwner.setRole(Role.employee);
                        userService.saveUser(teamOwner);
                    }
                }
                dbUser.setRole(user.getRole());
            }

            userService.saveUser(dbUser);
            return ResponseEntity.ok().build();

        } catch (DataAccessException | ManyTeamOwnerException d) {
            return ResponseEntity.badRequest().body("alreadyexists");
        }
    }

	@DeleteMapping(value = "/api/user/delete")
	public ResponseEntity<String> deleteUser(@RequestParam(required = true) Integer userId) {
		try {
			log.info("Borrando usuario con id:" + userId);
			userService.deleteUserById(userId);
			log.info("Usuario borrado correctamente");
			return ResponseEntity.ok("User Deleted");

		} catch (DataAccessException d) {
			log.error("Error: " + d);
			return ResponseEntity.badRequest().build();
		}

	}

	@GetMapping(value = "/api/user/credentials")
	public ResponseEntity<Map<String, Object>> getCredentials(HttpServletRequest r) {
	    Integer userId = (Integer)r.getSession().getAttribute("userId");
		Integer teamId =(Integer) r.getSession().getAttribute("teamId");
		Map<String, Object> m = new HashMap<>();
		UserTW user = userService.findUserById(userId);
		log.info("Comprobando si el usuario con id " + userId + " pertenece al team con id: " + teamId);
		if(user!=null&&user.getTeam().getId().equals(teamId)) {
			log.info("Generando json con informacion de privilegios");
			m.put("user", new StrippedUserImpl(user));
			m.put("isTeamManager", user.getRole().equals(Role.team_owner));
			log.info("Obteniendo informacion de belongs del usuario");
			List<Belongs> lb = belongsService.findCurrentUserBelongs(userId).stream().collect(Collectors.toList());
			m.put("currentDepartments", lb);
			log.info("Obteniendo informacion de participations del usuario");
			List<Participation> lp = participationService.findCurrentParticipationsUser(userId).stream()
				.collect(Collectors.toList());
			m.put("currentProjects", lp);
			log.info("Json generado correctamente");
			return ResponseEntity.ok(m);
		}else {
			log.error("Error el usuario no existe o no pertenece al departamento");
			return ResponseEntity.badRequest().build();
		}
	}

}
