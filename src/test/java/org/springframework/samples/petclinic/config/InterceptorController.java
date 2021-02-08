package org.springframework.samples.petclinic.config;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class InterceptorController {
	@GetMapping(value = "/api/InterceptorTest/Login")
	public ResponseEntity<String> loginInterceptor() {
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "/api/InterceptorTest/TeamOwner")
	public ResponseEntity<String> teamOwnerInterceptor() {
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "/api/InterceptorTest/DepartmentManager")
	public ResponseEntity<String> departmentManagerInterceptor() {
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "/api/InterceptorTest/ProjectManager")
	public ResponseEntity<String> projectManagerInterceptor() {
		return ResponseEntity.ok().build();
	}
	@GetMapping(value = "/api/InterceptorTest/ProjectEmployee")
	public ResponseEntity<String> projectEmployeeInterceptor() {
		return ResponseEntity.ok().build();
	}
}
