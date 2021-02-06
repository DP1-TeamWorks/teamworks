package org.springframework.samples.petclinic.validation;

public class ProjectLimitDepartmentException extends Exception{
	public ProjectLimitDepartmentException() {
		super("Many roject assigned to department");
	}
}
