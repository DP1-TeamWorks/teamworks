package org.springframework.samples.petclinic.validation;

public class ToDoLimitMilestoneException extends Exception {

	private static final long serialVersionUID = 1L;

	public ToDoLimitMilestoneException() {
		super("Many toDos assigned to milestone");
	}

}
