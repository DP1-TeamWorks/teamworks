package org.springframework.samples.petclinic.validation;

public class ToDoMaxToDosPerAssigneeException extends Exception {

	private static final long serialVersionUID = 1L;

	public ToDoMaxToDosPerAssigneeException() {
		super("You have too many toDos assigned");
	}

}
