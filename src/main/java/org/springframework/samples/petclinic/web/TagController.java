package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.TagService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class TagController {
    private final TagService tagService;
    private final ProjectService projectService;

    @Autowired
    public TagController(TagService tagService, ProjectService projectService) {
        this.tagService = tagService;
        this.projectService = projectService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping(value = "/api/tags")
    public List<Tag> getTagsByProjectId(HttpServletRequest r, @RequestParam(required = true) Integer projectId) {
        Project project = projectService.findProjectById(projectId);
        return project.getTags();
    }

    @PostMapping(value = "/api/tags")
    public ResponseEntity<String> createTag(HttpServletRequest r, @RequestBody Tag tag,
            @RequestParam(required = true) Integer projectId) {
        try {
            Project project = projectService.findProjectById(projectId);
            tag.setProject(project);
            tagService.saveTag(tag);
            return ResponseEntity.ok().build();

        } catch (DataAccessException d) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/api/tags")
    public ResponseEntity<String> deleteTagById(HttpServletRequest r, @RequestParam(required = true) Integer tagId) {
        try {
            tagService.deleteTagById(tagId);
            return ResponseEntity.ok().build();

        } catch (DataAccessException d) {
            return ResponseEntity.badRequest().build();
        }
    }

}
