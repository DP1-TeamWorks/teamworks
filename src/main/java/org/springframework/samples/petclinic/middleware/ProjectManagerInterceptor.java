package org.springframework.samples.petclinic.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ProjectManagerInterceptor extends HandlerInterceptorAdapter {
	private final UserTWService userTWService;
	private final BelongsService belongsService;
	private final ParticipationService participationService;
	private final ProjectService projectService;
	
	@Autowired
	public ProjectManagerInterceptor(UserTWService userTWService, BelongsService belongsService,ParticipationService participationService,ProjectService projectService) {
		this.belongsService = belongsService;
		this.userTWService = userTWService;
		this.participationService=participationService;
		this.projectService=projectService;
	}
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		Integer userId = (Integer) req.getSession().getAttribute("userId");
		Integer projectId = Integer.valueOf(req.getParameter("projectId"));
		Participation participation=participationService.findCurrentParticipation(userId, projectId);
		Project project=projectService.findProjectById(projectId);
		Belongs belongs = belongsService.findCurrentBelong(userId, project.getDepartment().getId());
		Boolean isDepartmentManager=false;
		Boolean isProjectManager = false;
		if (participation != null) {
			isProjectManager = participation.getIsProjectManager();
		}
		if (belongs != null) {
			isDepartmentManager = belongs.getIsDepartmentManager();
		}
		if (userTWService.findUserById(userId).getRole().equals(Role.team_owner) || isDepartmentManager||isProjectManager) {
			return true;
		} else {
			res.sendError(403);
			return false;
		}

	}

}
