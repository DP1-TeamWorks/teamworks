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
    private final ProjectService projectService;

    @Autowired
    public ProjectEmployeeInterceptor(UserTWService userTWService, MilestoneService milestoneService,
                                      ToDoService toDoService, ParticipationService participationService, BelongsService belongsService, ProjectService projectService) {
        this.userTWService = userTWService;
        this.participationService = participationService;
        this.toDoService = toDoService;
        this.milestoneService = milestoneService;
        this.belongsService = belongsService;
		this.projectService = projectService;
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
        Integer projectId = null;
        if (paramTodoId != null) {
        	todo = toDoService.findToDoById(Integer.valueOf(req.getParameter("toDoId")));
        	projectId = todo.getMilestone().getProject().getId();
        }
            
        if (paramMilestoneId != null) {
        	 milestone = milestoneService.findMilestoneById(Integer.valueOf(req.getParameter("milestoneId")));
        	projectId = milestone.getProject().getId();
        }
           

        
        if (paramProjectId != null){
        	projectId=Integer.valueOf(paramProjectId); 
        }
       

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
        	Integer dptId=null;
        	Team team =new Team();
        	if(milestone!=null) {
        		dptId =milestone.getProject().getDepartment().getId();
        		team=milestone.getProject().getDepartment().getTeam();
        	}else if(todo!=null) {
        		dptId=todo.getMilestone().getProject().getDepartment().getId();
        		team=todo.getAssignee().getTeam();
        	}
        	else if(projectId!=null){
        		Project project=projectService.findProjectById(projectId);
        		dptId=project.getDepartment().getId();
        		team=project.getDepartment().getTeam();
        	}else {
        		res.sendError(403);
        		return false;
        	}
            
            Belongs userBelongs = belongsService.findCurrentBelongs(userId, dptId);
            if((user.getRole().equals(Role.team_owner)&&user.getTeam().equals(team))) {
            	return true;
            }
            if ((userBelongs != null && userBelongs.getIsDepartmentManager()))
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
