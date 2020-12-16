package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	private final TeamService teamService;
	private final UserTWService userTWService;

	@Autowired
	public LoginController(TeamService teamService, UserTWService userTWService) {
		this.teamService = teamService;
		this.userTWService = userTWService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setAllowedFields("id");
	}

	@CrossOrigin
	@PostMapping(value = "/api/auth/login")
	public ResponseEntity<UserTW> login(HttpServletRequest r,@RequestBody Map<String,String> b ) {
		try {
			String email=b.get("mail");
			String password=b.get("password");
			UserTW user = userTWService.getLoginUser(email, password);
			if(user!=null) {
				r.getSession().setAttribute("userId",user.getId());
				r.getSession().setAttribute("teamId", user.getTeam().getId());
				
				return ResponseEntity.accepted().body(user);
			}
			else {
				return ResponseEntity.badRequest().build();
			}
			
			
		}

		catch (DataAccessException d) {
			return null;
		}
	}

}
