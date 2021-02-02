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
		if(user.getName().length()<2||user.getName().length()>25) {
			errors.rejectValue("name", "El tamaño del nombre debe estar entre 2 y 25");
		}
		if(!user.getName().matches("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]+$")) {
			errors.rejectValue("name", "El nombre debe estar compuesto solo por letras");
		}
		if(user.getLastname().length()<2||user.getLastname().length()>40) {
			errors.rejectValue("Lastname", "El tamaño de los appellidos debe estar entre 2 y 40");
		}
		if(!user.getLastname().matches("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]+$")) {
			errors.rejectValue("Lastname", "Los apellidos deben estar compuesto solo por letras");
		}
		if(user.getPassword().length()<8||user.getPassword().length()>25) {
			errors.rejectValue("Password", "El tamaño de la contraseña debe estar entre 2 y 25");
		}
		
		
	}
	
}
