package org.springframework.samples.petclinic.validation;

public class ManyDepartmentManagerException extends Exception {

	private static final long serialVersionUID = 1L;

	public ManyDepartmentManagerException() {
		super("More than one department manager");
	}
}
