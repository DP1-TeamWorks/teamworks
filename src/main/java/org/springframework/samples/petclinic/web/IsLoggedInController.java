package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class IsLoggedInController {

    // TODO: Remove all CrossOrigin annotations
    @CrossOrigin
	@GetMapping(value= "/api/auth/islogged")
	public ResponseEntity get() {
	    // Since we use the login middleware, if we got this far it means we're logged in, so just return 200
		return ResponseEntity.ok().build();
	}

}
