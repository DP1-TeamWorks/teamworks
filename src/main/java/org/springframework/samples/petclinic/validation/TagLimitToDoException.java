package org.springframework.samples.petclinic.validation;

public class TagLimitToDoException extends Exception{
	public TagLimitToDoException() {
		super("Many Tags assigned to toDo");
	}
}
