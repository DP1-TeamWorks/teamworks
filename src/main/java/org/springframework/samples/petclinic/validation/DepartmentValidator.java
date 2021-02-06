package org.springframework.samples.petclinic.validation;

import org.springframework.samples.petclinic.model.Department;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class DepartmentValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return Department.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Department department=(Department) obj;
		if(department.getName()!=null&&(department.getName().length()<1||department.getName().length()>25)) {
			errors.rejectValue("name", "The name size must be between 1 and 25");
		}
		
		if(department.getDescription()!=null&&(department.getDescription().length()<1||department.getDescription().length()>3000)) {
			errors.rejectValue("Descrption", "The descrption size must be between 1 and 3000");
		}
		
		
		
		
	}
}
