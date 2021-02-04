package org.springframework.samples.petclinic.validation;

import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class TeamValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return UserTW.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Team team=(Team) obj;
		if(team.getName().length()<1||team.getName().length()>25) {
			errors.rejectValue("name", "The name size must be between 1 and 25");
		}
		if(!team.getName().matches("^[A-Za-zÀ-ÿ0-9\\u00f1\\u00d1_-]*$")) {
			errors.rejectValue("name", "Wrong name format");
		}
		
		if(team.getIdentifier().length()<1||team.getIdentifier().length()>120) {
			errors.rejectValue("identifier", "The Identifier size must be between 1 and 120");
		}
		if(!team.getIdentifier().matches("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]+$")) {
			errors.rejectValue("identifier", "The Identifier must contain only letters ");
		}
		
		
	}
}
