package org.springframework.samples.petclinic.validation;

public class ManyTeamOwnerException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ManyTeamOwnerException() {
		super("More than one team owner");
	}
}
