package org.springframework.samples.petclinic.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final UserTWService userTWService;

    public LoginInterceptor(UserTWService userTWService) {
        this.userTWService = userTWService;
    }

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse res, Object handler) throws Exception {

        Integer userId = (Integer)req.getSession().getAttribute("userId");
        // We need to query the user in case it was deleted
        UserTW user = userTWService.findUserById(userId);
        if (user == null)
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
