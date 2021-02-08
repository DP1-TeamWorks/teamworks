package org.springframework.samples.petclinic.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.service.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ProjectManagerInterceptor extends HandlerInterceptorAdapter {
	private final UserTWService userTWService;
	private final BelongsService belongsService;
	private final ParticipationService participationService;
	private final ProjectService projectService;
	private final MilestoneService milestoneService;

	@Autowired
	public ProjectManagerInterceptor(UserTWService userTWService, BelongsService belongsService,
			ParticipationService participationService, ProjectService projectService, MilestoneService milestoneService) {
		this.belongsService = belongsService;
		this.userTWService = userTWService;
		this.participationService = participationService;
		this.projectService = projectService;
		this.milestoneService = milestoneService;
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		Integer userId = (Integer) req.getSession().getAttribute("userId");
		UserTW user = userTWService.findUserById(userId);
		Integer projectId = Integer.valueOf(req.getParameter("projectId"));
		if (projectId == null)
        {
            // find by milestone
            Integer milestoneId = Integer.valueOf(req.getParameter("milestoneId"));
            if (milestoneId == null)
            {
                res.sendError(400);
                return false;
            }
            Milestone m = milestoneService.findMilestoneById(milestoneId);
            if (m == null)
            {
                res.sendError(403);
                return false;
            }
            // set project id accordingly to run subsequent checks
            projectId = m.getProject().getId();
        }
		Participation participation = participationService.findCurrentParticipation(userId, projectId);
		Project project = projectService.findProjectById(projectId);
		Belongs belongs = belongsService.findCurrentBelongs(userId, project.getDepartment().getId());
		Boolean isDepartmentManager = false;
		Boolean isProjectManager = false;
		if (participation != null) {
			isProjectManager = participation.getIsProjectManager();
		}
		if (belongs != null) {
			isDepartmentManager = belongs.getIsDepartmentManager();
		}
		if (user.getTeam().equals(project.getDepartment().getTeam()) && user.getRole().equals(Role.team_owner)
				|| isDepartmentManager || isProjectManager) {
			return true;
		} else {
			res.sendError(403);
			return false;
		}

	}

}
