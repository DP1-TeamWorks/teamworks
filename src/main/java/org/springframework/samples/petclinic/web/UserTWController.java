package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.bind.WebDataBinder;
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
	public UserTWController(UserTWService userService) {
		this.userService = userService;
	}
	
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/*
	@GetMapping(value = "/userTW")
	public List<UserTW> getUser(@RequestParam(required = false) String username) {
	    if (username == null)
        {
            List<UserTW> list = userService.getAllUsers().stream().collect(Collectors.toList());
            return list;
        }
	    else
        {
	    	UserTW a = userService.findUserByName(username);
            if (a == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user");
            else
            {
                List<UserTW> list = new ArrayList<>();
                list.add(a);
                return list;
            }
        }
	}
	
	 */
	
	
	@PostMapping(value = "/UserTW")
	public ResponseEntity postUser(@RequestBody UserTW user) {
        userService.saveUser(user);
        return ResponseEntity.noContent().build();
	}

}
