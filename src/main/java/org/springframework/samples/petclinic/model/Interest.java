package org.springframework.samples.petclinic.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "interests")
public class Interest extends NamedEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private NewUser user;

    public NewUser getUser() {
        return user;
    }
}
