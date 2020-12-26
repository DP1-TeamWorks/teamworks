package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

public class MileStoneControler {
	private final MilestoneService milestoneService;
	private final ProjectService projectService;

	public MileStoneControler(MilestoneService milestoneService, ProjectService projectService) {
		this.milestoneService = milestoneService;
		this.projectService = projectService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/api/milestones")
	public List<Milestone> getMilestones(@RequestParam(required = false) String name) {
		List<Milestone> l = new ArrayList<>();
		if (name == null) {
			l = milestoneService.getAllMilestone().stream().collect(Collectors.toList());

		} else {
			l = milestoneService.findMilestoneByName(name).stream().collect(Collectors.toList());

		}
		return l;
	}

	@PostMapping(value = "/api/milestones")
	public ResponseEntity<String> postMilestones(@RequestParam(required = true) Integer projectId,
			@RequestBody Milestone milestone) {

		try {
			Project project = projectService.findProjectById(projectId);

			milestone.setProject(project);
			milestoneService.saveMilestone(milestone);
			return ResponseEntity.ok("Milestone create");
		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}

	@DeleteMapping(value = "/api/milestones")
	public ResponseEntity<String> deleteMilestones(@RequestParam(required = true) Integer milestoneId) {
		try {
			milestoneService.deleteMilestonetById(milestoneId);
			return ResponseEntity.ok("Milestone delete");
		} catch (DataAccessException d) {
			return ResponseEntity.notFound().build();
		}

	}

}
