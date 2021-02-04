package org.springframework.samples.petclinic.validation;

public class TagLimitProjectException extends Exception{
	public TagLimitProjectException() {
		super("Many Tags assigned to project");
	}
}
