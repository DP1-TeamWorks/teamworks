package org.springframework.samples.petclinic.validation;

public class DateIncoherenceException extends Exception {

	private static final long serialVersionUID = 1L;

	public DateIncoherenceException() {
		super("Initial date must be before final date");
	}
}
