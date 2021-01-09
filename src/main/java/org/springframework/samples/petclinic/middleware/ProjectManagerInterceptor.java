package org.springframework.samples.petclinic.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ProjectManagerInterceptor extends HandlerInterceptorAdapter {
    private final UserTWService userTWService;
    private final ParticipationService participationService;

    @Autowired
    public ProjectManagerInterceptor(UserTWService userTWService, ParticipationService participationService) {
        this.participationService = participationService;
        this.userTWService = userTWService;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        Integer projectId = Integer.valueOf(req.getParameter("projectId"));
        Participation participation = participationService.findCurrentParticipation(userId, projectId);
        Boolean isProjectManager = false;

        if (participation != null) {
            isProjectManager = participation.getIsProjectManager();
        }
        if (userTWService.findUserById(userId).getRole().equals(Role.team_owner) || isProjectManager) {
            return true;
        } else {
            res.sendError(403);
            return false;
        }

    }
}
