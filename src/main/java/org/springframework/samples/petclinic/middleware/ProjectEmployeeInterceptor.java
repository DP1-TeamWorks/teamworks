package org.springframework.samples.petclinic.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.service.MilestoneService;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.ToDoService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ProjectEmployeeInterceptor extends HandlerInterceptorAdapter {
    private final UserTWService userTWService;
    private final ParticipationService participationService;
    private final ToDoService toDoService;
    private final MilestoneService milestoneService;

    @Autowired
    public ProjectEmployeeInterceptor(UserTWService userTWService, MilestoneService milestoneService,
            ToDoService toDoService, ParticipationService participationService) {
        this.userTWService = userTWService;
        this.participationService = participationService;
        this.toDoService = toDoService;
        this.milestoneService = milestoneService;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        Integer projectId = req.getParameter("projectId") == null ? ((req.getParameter("milestoneId") == null)
                ? toDoService.findToDoById(Integer.valueOf(req.getParameter("toDoId"))).getMilestone().getProject()
                        .getId()
                : milestoneService.findMilestoneById(Integer.valueOf(req.getParameter("milestoneId"))).getProject()
                        .getId())
                : Integer.valueOf(req.getParameter("projectId"));
        Participation participation = participationService.findCurrentParticipation(userId, projectId);
        /*
         * Se podría plantear q solo puediera marcar el todo como done el asignado, pero
         * no es necesario: Boolean isToDoAsignee = req.getParameter("toDoId") != null ?
         * true : toDoService.findToDoById(Integer.valueOf(req.getParameter("toDoId"))).
         * getAssignee().getId() == userId;
         */
        Boolean isProjectEmployee = participation != null;

        if (isProjectEmployee) {
            return true;
        } else {
            res.sendError(403);
            return false;
        }

    }

}
