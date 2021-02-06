package org.springframework.samples.petclinic.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DepartmentManagerInterceptor extends HandlerInterceptorAdapter {
	private final UserTWService userTWService;
	private final BelongsService belongsService;
	private final DepartmentService departmentService;

	@Autowired
	public DepartmentManagerInterceptor(UserTWService userTWService, BelongsService belongsService, DepartmentService departmentService) {
		this.belongsService = belongsService;
		this.userTWService = userTWService;
		this.departmentService = departmentService;
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		Integer userId = (Integer) req.getSession().getAttribute("userId");
		UserTW user = userTWService.findUserById(userId);
		Integer departmentId = Integer.valueOf(req.getParameter("departmentId"));
		if (departmentId == null)
        {
            res.sendError(400);
            return false;
        }
        Department department = departmentService.findDepartmentById(departmentId);
        if (department == null)
        {
            res.sendError(400);
            return false;
        }
		Belongs belongs = belongsService.findCurrentBelongs(userId, departmentId);
		Boolean isDepartmentManager = false;

		if (user.getTeam().equals(department.getTeam()) && user.getRole().equals(Role.team_owner))
		    return true;
		if (belongs == null || !belongs.getIsDepartmentManager())
        {
            res.sendError(403);
            return false;
        }
		else
        {
            return true;
        }
	}
}
