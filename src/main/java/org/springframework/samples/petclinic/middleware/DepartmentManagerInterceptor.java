package org.springframework.samples.petclinic.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DepartmentManagerInterceptor extends HandlerInterceptorAdapter {
	private final UserTWService userTWService;
	private final BelongsService belongsService;

	@Autowired
	public DepartmentManagerInterceptor(UserTWService userTWService, BelongsService belongsService) {
		this.belongsService = belongsService;
		this.userTWService = userTWService;
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		Integer userId = (Integer) req.getSession().getAttribute("userId");
		Integer departmentId = Integer.valueOf(req.getParameter("departmentId"));
		Belongs belongs = belongsService.findCurrentBelongs(userId, departmentId);
		Boolean isDepartmentManager = false;

		if (belongs != null) {
			isDepartmentManager = belongs.getIsDepartmentManager();
		}
		if (userTWService.findUserById(userId).getRole().equals(Role.team_owner) || isDepartmentManager) {
			return true;
		} else {
			res.sendError(403);
			return false;
		}

	}
}
