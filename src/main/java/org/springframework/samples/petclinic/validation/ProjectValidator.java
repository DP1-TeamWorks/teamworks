package org.springframework.samples.petclinic.validation;


import org.springframework.samples.petclinic.model.Project;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class ProjectValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Project project=(Project) obj;
		if(project.getName()!=null&&(project.getName().length()<1||project.getName().length()>25)) {
			errors.rejectValue("name", "The name size must be between 1 and 25");
		}
		if(project.getDescription()!=null&&(project.getDescription().length()<1||project.getDescription().length()>3000)) {
			errors.rejectValue("description", "The description size must be between 1 and 3000");
		}
		
		
	}

}
