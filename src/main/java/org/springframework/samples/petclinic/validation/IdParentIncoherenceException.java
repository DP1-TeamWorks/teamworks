package org.springframework.samples.petclinic.validation;

public class IdParentIncoherenceException extends Exception {
    private static final long serialVersionUID = 1L;

    public IdParentIncoherenceException(String parent, String child) {
        super("The " + parent + "doesn't contain the " + child + " id");
    }
}
