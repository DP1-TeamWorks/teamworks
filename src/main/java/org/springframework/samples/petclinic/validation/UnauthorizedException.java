package org.springframework.samples.petclinic.validation;

public class UnauthorizedException extends Exception {
    private static final long serialVersionUID = 1L;

    public UnauthorizedException() {
        super("Many toDos assigned to milestone");
    }
}
