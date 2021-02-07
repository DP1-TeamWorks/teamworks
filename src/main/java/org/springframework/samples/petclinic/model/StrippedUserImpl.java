package org.springframework.samples.petclinic.model;

import org.springframework.samples.petclinic.model.UserTW.StrippedUser;

public class StrippedUserImpl implements StrippedUser {

    private String name;
    private String lastName;
    private Integer id;
    private String email;

    public StrippedUserImpl(UserTW user) {
        this.name = user.name;
        this.lastName = user.lastname;
        this.id = user.id;
        this.email = user.email;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getLastname() {
        return this.lastName;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

}
