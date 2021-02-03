package org.springframework.samples.petclinic.validation;

public class ManyDepartmentManagerException extends Exception {
	public ManyDepartmentManagerException() {
		super("More than one department manager");
	}
}
