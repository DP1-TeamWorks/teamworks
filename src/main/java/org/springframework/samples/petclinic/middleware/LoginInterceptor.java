package org.springframework.samples.petclinic.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse res, Object handler) throws Exception {

        Integer userId = (Integer)req.getSession().getAttribute("userId");
        if (userId == null)
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
