package org.springframework.samples.petclinic.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.enums.Role;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.service.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ProjectEmployeeInterceptor extends HandlerInterceptorAdapter {
    private final UserTWService userTWService;
    private final ParticipationService participationService;
    private final ToDoService toDoService;
    private final MilestoneService milestoneService;
    private final BelongsService belongsService;

    @Autowired
    public ProjectEmployeeInterceptor(UserTWService userTWService, MilestoneService milestoneService,
                                      ToDoService toDoService, ParticipationService participationService, BelongsService belongsService) {
        this.userTWService = userTWService;
        this.participationService = participationService;
        this.toDoService = toDoService;
        this.milestoneService = milestoneService;
        this.belongsService = belongsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        UserTW user = userTWService.findUserById(userId);
        ToDo todo = null;
        Milestone milestone = null;
        String paramTodoId = req.getParameter("toDoId");
        String paramMilestoneId = req.getParameter("milestoneId");
        String paramProjectId = req.getParameter("projectId");
        if (paramTodoId != null)
            todo = toDoService.findToDoById(Integer.valueOf(req.getParameter("toDoId")));
        if (paramMilestoneId != null)
            milestone = milestoneService.findMilestoneById(Integer.valueOf(req.getParameter("milestoneId")));

        Integer projectId = null;
        if (paramProjectId == null)
        {
            if (paramMilestoneId == null)
                projectId = todo.getMilestone().getProject().getId();
            else
                projectId = milestone.getProject().getId();
        }
        else
            projectId = Integer.valueOf(paramProjectId);

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
            Integer dptId = milestone != null ? milestone.getProject().getDepartment().getId() : todo.getMilestone().getProject().getDepartment().getId();
            Belongs userBelongs = belongsService.findCurrentBelongs(userId, dptId);
            if ((userBelongs != null && userBelongs.getIsDepartmentManager()) || user.getRole().equals(Role.team_owner))
            {
                return true;
            } else
            {
                res.sendError(403);
                return false;
            }
        }

    }

}
