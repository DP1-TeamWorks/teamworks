package org.springframework.samples.petclinic.validation;



import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserTW.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		UserTW user=(UserTW) obj;
		if(user.getName().length()<1||user.getName().length()>25) {
			errors.rejectValue("name", "The name size must be between 1 and 25");
		}
		if(!user.getName().matches("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]+$")) {
			errors.rejectValue("name", "The name must contain only letters");
		}
		if(user.getLastname().length()<1||user.getLastname().length()>120) {
			errors.rejectValue("Lastname", "The Lastname size must be between 1 and 120");
		}
		if(!user.getLastname().matches("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]+$")) {
			errors.rejectValue("Lastname", "The Lastname must contain only letters");
		}
		if(user.getPassword().length()<8||user.getPassword().length()>25) {
			errors.rejectValue("Password", "The password size must be between 8 and 25");
		}
		
		
	}
	
}
