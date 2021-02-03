package org.springframework.samples.petclinic.validation;

public class ManyProjectManagerException extends Exception {
	public ManyProjectManagerException() {
		super("More than one project manager");
	}
}
