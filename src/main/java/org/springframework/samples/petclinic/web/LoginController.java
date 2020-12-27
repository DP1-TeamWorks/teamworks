package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	private final UserTWService userTWService;
	
	@Autowired
	public LoginController(TeamService teamService,UserTWService userTWService) {
		this.userTWService=userTWService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setAllowedFields("id");
	}
	
	@GetMapping(value ="/api/auth/login")
	public UserTW login(@RequestParam(required=true) String email,@RequestParam(required=true) String password) {
		try{
			UserTW user=userTWService.getLoginUser(email, password);
			return user;
		}
		
		catch (DataAccessException d) {
			
			System.out.println("dsksdfknmsadjkosdaoo");
			return null;
		}
	}
	
}
