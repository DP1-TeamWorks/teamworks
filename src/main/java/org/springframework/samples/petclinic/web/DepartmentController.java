package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.DepartmentService;
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
public class DepartmentController {
	private final DepartmentService departmentService;
	private final TeamService teamService;
	@Autowired
	public DepartmentController(DepartmentService departmentService,TeamService teamService) {
		this.departmentService = departmentService;
		this.teamService=teamService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/departments")
	public List<Department> getDeparments(@RequestParam(required = false) String name){
		List<Department> l=new ArrayList<>();
		if(name==null) {
			l = departmentService.getAllDepartments().stream().collect(Collectors.toList());
            return l;
		}
		else {
			l=departmentService.findDepartmentByName(name).stream().collect(Collectors.toList());
			return l;
		}
	}
	
	@PostMapping(value = "/departments")
	public ResponseEntity<String> postDeparments(@RequestParam(required = true) Integer teamId,@RequestBody Department department) {
		try {
			Team team=teamService.findTeamById(teamId);
			department.setTeam(team);
			departmentService.saveDepartment(department);
			return ResponseEntity.ok("Department create");
		}
		catch(DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	    
	}
	
	@DeleteMapping(value = "/departments")
	public ResponseEntity<String> deleteDeparments(@RequestParam(required = true) Integer departmentId) {
		System.out.println(departmentId);
		try {
			departmentService.deleteDepartmentById(departmentId);
	        return ResponseEntity.ok("Department delete");
		}
		catch(DataAccessException d) {
			return ResponseEntity.notFound().build();
		}
	    
	}
}
