package org.springframework.samples.petclinic.validation;

public class ManyProjectManagerException extends Exception {

	private static final long serialVersionUID = 1L;

	public ManyProjectManagerException() {
		super("More than one project manager");
	}
}
