package org.springframework.samples.petclinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "interests")
public class Interest extends NamedEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    @JsonBackReference
    private NewUser user;

    public Interest() {
    }

    public Interest(String s)
    {
        this.setName(s);
    }
}
