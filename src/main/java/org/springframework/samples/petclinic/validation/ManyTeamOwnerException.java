package org.springframework.samples.petclinic.validation;

public class ManyTeamOwnerException extends Exception{
	public ManyTeamOwnerException() {
		super("More than one team owner");
	}
}
