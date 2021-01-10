package org.springframework.samples.petclinic.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TeamOwnerInterceptor extends HandlerInterceptorAdapter {

	private final UserTWService userTWService;

	public TeamOwnerInterceptor(UserTWService userTWService) {
		this.userTWService = userTWService;
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		Integer userId = (Integer) req.getSession().getAttribute("userId");
		UserTW user = userTWService.findUserById(userId);

		if (user.getRole().equals(Role.team_owner)) {
			return true;
		} else {
			res.sendError(403);
			return false;

		}
	}

}
