package org.springframework.samples.petclinic.model;

import org.springframework.samples.petclinic.enums.Role;
import org.springframework.samples.petclinic.model.UserTW.StrippedUser;

import java.time.LocalDate;

public class StrippedUserImpl implements StrippedUser {

    private String name;
    private String lastName;
    private Integer id;
    private String email;
    private LocalDate joinDate;
    private Role role;

    public StrippedUserImpl(UserTW user) {
        if (user == null)
            return;

        this.name = user.name;
        this.lastName = user.lastname;
        this.id = user.id;
        this.email = user.email;
        this.joinDate = user.joinDate;
        this.role = user.role;
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

    @Override
    public LocalDate getJoinDate() {
        return this.joinDate;
    }

    @Override
    public Role getRole() {
        return this.role;
    }

}
