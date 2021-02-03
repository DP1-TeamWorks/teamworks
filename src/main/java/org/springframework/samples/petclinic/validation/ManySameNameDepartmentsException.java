package org.springframework.samples.petclinic.validation;

public class ManySameNameDepartmentsException extends Exception {
    private static final long serialVersionUID = 1L;

    public ManySameNameDepartmentsException() {
        super("More than one department with the same name");
    }
}
