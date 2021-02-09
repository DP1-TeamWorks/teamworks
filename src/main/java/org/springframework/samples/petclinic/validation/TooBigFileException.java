package org.springframework.samples.petclinic.validation;

public class TooBigFileException extends Exception {
    private static final long serialVersionUID = 1L;

    public TooBigFileException() {
        super("One of the files is too Big");
    }

}
