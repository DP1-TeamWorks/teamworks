package org.springframework.samples.petclinic.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletResponse r) throws IOException {
        //do something like logging
        return "index.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
