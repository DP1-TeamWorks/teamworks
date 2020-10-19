package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.ArrayList;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    
		List<Person> people = new ArrayList<Person>(); 
		Person Jose = new Person();
		Jose.setFirstName("Jose");
		Jose.setLastName("Snow");
		
		Person Pedro = new Person();
		Pedro.setFirstName("Pedro");
		Pedro.setLastName("Pon");

		people.add(Jose);
		people.add(Pedro);
		System.out.println(people);

		model.put("people", people);
		model.put("title", "My project");
		model.put("group", "Developers");
		
		return "welcome";
	  }
}
