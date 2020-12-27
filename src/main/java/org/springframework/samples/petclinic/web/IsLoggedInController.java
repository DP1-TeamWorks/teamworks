package org.springframework.samples.petclinic.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class IsLoggedInController {
	@GetMapping(value = "/api/auth/islogged")
	public ResponseEntity get() {
		// Since we use the login middleware, if we got this far it means we're logged
		// in, so just return 200
		return ResponseEntity.ok().build();
	}

}
