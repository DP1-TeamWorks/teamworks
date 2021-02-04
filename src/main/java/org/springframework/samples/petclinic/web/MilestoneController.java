package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Milestone;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.service.MilestoneService;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class MilestoneController {
	private final MilestoneService milestoneService;
	private final ProjectService projectService;

	@Autowired
	public MilestoneController(MilestoneService milestoneService, ProjectService projectService) {
		this.milestoneService = milestoneService;
		this.projectService = projectService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/api/milestones/next")
	public Milestone getNextMilestone(@RequestParam(required = true) Integer projectId) {
		log.info("Obteniendo milestone más próxima del proyecto con id: "+projectId);
		return milestoneService.findNextMilestone(projectId);
	}

	@GetMapping(value = "/api/milestones")
	public List<Milestone> getMilestones(@RequestParam(required = false) String name) {
		List<Milestone> l = new ArrayList<>();
		log.info("Obteniendo todas las milestones");
		l = milestoneService.getAllMilestone().stream().collect(Collectors.toList());

		return l;
	}

	@PostMapping(value = "/api/milestones")
	public ResponseEntity<String> postMilestones(@RequestParam(required = true) Integer projectId,
			@Valid @RequestBody Milestone milestone) {

		try {
			log.info("Milestone validada correctamente");
			Project project = projectService.findProjectById(projectId);
			log.info("Añadiendo milestone al proyecto");
			milestone.setProject(project);
			log.info("Guradando milestone");
			milestoneService.saveMilestone(milestone);
			log.info("Milestone guradad correctamente");
			return ResponseEntity.ok("Milestone create");
		} catch (DataAccessException d) {
			log.error("Error: "+d.getMessage());
			return ResponseEntity.badRequest().build();
		}

	}

	@DeleteMapping(value = "/api/milestones")
	public ResponseEntity<String> deleteMilestones(@RequestParam(required = true) Integer milestoneId) {
		try {
			log.info("Borrando milestone con id: " +milestoneId);
			milestoneService.deleteMilestonetById(milestoneId);
			log.info("Milestone borrada correctamente");
			return ResponseEntity.ok("Milestone delete");
		} catch (DataAccessException d) {
			log.error("Error: "+d.getMessage());
			return ResponseEntity.notFound().build();
		}

	}

}
