package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.TagService;
import org.springframework.samples.petclinic.validation.TagLimitProjectException;
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
        log.info("Obteniendo tags del proyecto con id: "+projectId);
    	Project project = projectService.findProjectById(projectId);
        return project.getTags();
    }

    @PostMapping(value = "/api/tags")
    public ResponseEntity<String> createTag(HttpServletRequest r,@Valid @RequestBody Tag tag,
            @RequestParam(required = true) Integer projectId) {
        try {
        	log.info("Tag validad con exito");
            Project project = projectService.findProjectById(projectId);
            log.info("Añadiendo tag al proyecto");
            tag.setProject(project);
            log.info("Guardando tag");
            tagService.saveTag(tag);
            log.info("Tag guardada correctamente");
            return ResponseEntity.ok().build();

        } catch (DataAccessException | TagLimitProjectException d) {
        	log.error("Error: "+d.getMessage());
            return ResponseEntity.badRequest().body(d.getMessage());
        }
    }

    @DeleteMapping(value = "/api/tags")
    public ResponseEntity<String> deleteTagById(HttpServletRequest r, @RequestParam(required = true) Integer tagId) {
        try {
        	log.info("Borrando tag con id: "+tagId);
            tagService.deleteTagById(tagId);
            log.info("Tag borrada correctamente");
            return ResponseEntity.ok().build();

        } catch (DataAccessException d) {
        	log.error("Error: "+d.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
