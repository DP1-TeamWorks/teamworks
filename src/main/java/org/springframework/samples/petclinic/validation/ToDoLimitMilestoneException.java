package org.springframework.samples.petclinic.validation;

public class ToDoLimitMilestoneException extends Exception {

	private static final long serialVersionUID = 1L;

	public ToDoLimitMilestoneException() {
		super("You have too many toDos assigned");
	}

}
