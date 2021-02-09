package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.TagService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.TagLimitProjectException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TagController {

    private final TagService tagService;
    private final ProjectService projectService;
    private final UserTWService userTWService;

    @Autowired
    public TagController(TagService tagService, ProjectService projectService, UserTWService userTWService) {
        this.tagService = tagService;
        this.projectService = projectService;
        this.userTWService = userTWService;
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

    @GetMapping(value = "/api/tags/mine/all")
    public Map<String, List<Tag>> getAllMyTagsByProject(HttpServletRequest r) {
        UserTW user = userTWService.findUserById((Integer) r.getSession().getAttribute("userId"));
        log.info("Obteniendo todas las tags por proyecto del usuario");
        Map<String, List<Tag>> tags = user.getParticipation().stream().filter(p -> p.getFinalDate() == null)
                .map(Participation::getProject).map(Project::getTags).flatMap(Collection::stream)
                .collect(Collectors.groupingBy(tag -> tag.getProject().getName()));

        log.info(tags.toString());

        return tags;
    }

    @PostMapping(value = "/api/tags/create")
    public ResponseEntity<String> createTag(HttpServletRequest r, @Valid @RequestBody Tag tag,
            @RequestParam(required = true) Integer projectId) {
        try {
        	log.info("Tag validada con exito");
            Project project = projectService.findProjectById(projectId);
            tag.setProject(project);
            log.info("Guardando tag");
            tagService.saveTag(tag);
            log.info("Tag guardada correctamente");
            return ResponseEntity.ok().build();
        } catch (DataAccessException | TagLimitProjectException d) {
            log.error("Error:",d);
        	if (d.getClass() == TagLimitProjectException.class)
            {
                return ResponseEntity.badRequest().body("toomany");
            } else
            {
                return ResponseEntity.badRequest().body("alreadyexists");
            }

        }
    }

    @DeleteMapping(value = "/api/tags/delete")
    public ResponseEntity<String> deleteTagById(HttpServletRequest r, @RequestParam(required = true) Integer projectId, @RequestParam(required = true) Integer tagId) {
        try {
        	log.info("Borrando tag con id: "+tagId);
            tagService.deleteTagById(tagId);
            log.info("Tag borrada correctamente");
            return ResponseEntity.ok().build();
        } catch (DataAccessException d) {
        	log.error("Error: ",d);
        	return ResponseEntity.badRequest().build();
        }
    }
}
