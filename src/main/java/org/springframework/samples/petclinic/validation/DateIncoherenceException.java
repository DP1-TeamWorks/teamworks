package org.springframework.samples.petclinic.validation;

public class DateIncoherenceException extends Exception{
	public DateIncoherenceException() {
		super("Initial date must be before final date");
	}
}
