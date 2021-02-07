package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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

    @GetMapping(value = "/api/milestone")
    public Milestone getMilestone(@RequestParam(required = true) Integer projectId, @RequestParam(required = true) Integer milestoneId) {
        return milestoneService.findMilestoneById(milestoneId);
    }

	@GetMapping(value = "/api/milestones/next")
	public Milestone getNextMilestone(@RequestParam(required = true) Integer projectId) {
		return milestoneService.findNextMilestone(projectId);
	}

	@GetMapping(value = "/api/milestones")
	public List<Milestone> getMilestones(@RequestParam(required = true) Integer projectId) {
		return milestoneService.findMilestonesForProject(projectId)
            .stream()
            .sorted((a, b) ->
            {
                if (a.getDueFor().isBefore(LocalDate.now()))
                {
                    if (b.getDueFor().isBefore(LocalDate.now()))
                    {
                        return a.getDueFor().compareTo(b.getDueFor());
                    }
                    else
                    {
                        return 1;
                    }
                } else
                {
                    if (b.getDueFor().isBefore(LocalDate.now()))
                    {
                        return -1;
                    }
                    else
                    {
                        return a.getDueFor().compareTo(b.getDueFor());
                    }
                }
            })
            .collect(Collectors.toList());
	}

	@PostMapping(value = "/api/milestones")
	public ResponseEntity<String> postMilestones(@RequestParam(required = true) Integer projectId,
			@RequestBody Milestone milestone) {

		try {
			Project project = projectService.findProjectById(projectId);
            milestone.setProject(project);
			if (milestone.getId() == null)
            {
                milestoneService.saveMilestone(milestone);
                return ResponseEntity.ok("Milestone create");
            }
			else
            {
                Milestone dbMile = milestoneService.findMilestoneById(milestone.getId());
                if (dbMile == null || dbMile.getProject().getId() != projectId)
                    return ResponseEntity.badRequest().build();
                if (milestone.getName() != null)
                    dbMile.setName(milestone.getName());
                if (milestone.getDueFor() != null)
                    dbMile.setDueFor(milestone.getDueFor());
                milestoneService.saveMilestone(dbMile);
                return ResponseEntity.ok("Milestone updated");
            }
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
