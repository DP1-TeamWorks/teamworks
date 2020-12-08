package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserTWController {

	
	private final UserTWService userService;
	
	@Autowired
	public UserTWController(UserTWService userService, TeamService teamService) {
		this.userService = userService;
	}
	
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	@GetMapping(value = "/userTW")
	public List<UserTW> getUser(@RequestParam(required = false) String username) {
	    if (username == null) {
            List<UserTW> list = userService.getAllUsers().stream().collect(Collectors.toList());
            return list;
        }
	    else {
	    	List<UserTW> list = userService.findUserByName(username).stream().collect(Collectors.toList());
            if (list == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user");
            else {
                return list;
            }
        }
	}
	
	
	
	@PostMapping(value = "/userTW")
	public ResponseEntity<String> postUser(@RequestBody UserTW user) {
        try {
        	userService.saveUser(user);
            //return ResponseEntity.noContent().build();
        	return ResponseEntity.ok("User Created");
        } catch (DataAccessException d) {
        	return ResponseEntity.badRequest().build();
        }
	}
	
	
	@DeleteMapping(value= "/userTW")
	public ResponseEntity<String> deleteUser(@RequestParam(required=true) Integer userTWId){
		System.out.println("Delete user: "+ userTWId);
	try {
    	userService.deleteUserById(userTWId);
		return ResponseEntity.ok("User Deleted");

	} catch (DataAccessException d) {
    	return ResponseEntity.badRequest().build();
	}
	
	}

}
