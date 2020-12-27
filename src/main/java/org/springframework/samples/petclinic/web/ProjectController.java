package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
	private final DepartmentService departmentService;
	private final ProjectService projectService;
	@Autowired
	public ProjectController(DepartmentService departmentService,ProjectService projectService) {
		this.departmentService = departmentService;
		this.projectService=projectService;
	}
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	@GetMapping(value = "/api/projects")
	public List<Project> getProjects(@RequestParam(required = false) Integer departmentId){
		List<Project> l=new ArrayList<>();
		l= departmentService.findDepartmentById(departmentId).getProjects();
		return l;
	}
	@PostMapping(value = "/api/projects")
	public ResponseEntity<String> postProjects(@RequestParam(required = true) Integer departmentId,@RequestBody Project project) {
		
				try {
					Department depar=departmentService.findDepartmentById(departmentId);
					project.setDepartment(depar);
					projectService.saveProject(project);
					return ResponseEntity.ok("Project create");
				}
				catch(DataAccessException d) {
					return ResponseEntity.badRequest().build();
				}
			
	    
	}
	@DeleteMapping(value = "/api/projects")
	public ResponseEntity<String> deleteProjects(@RequestParam(required = true) Integer projectId) {
		try {
			projectService.deleteProjectById(projectId);
	        return ResponseEntity.ok("Project delete");
		}
		catch(DataAccessException d) {
			return ResponseEntity.notFound().build();
		}
	    
	}
	
}
