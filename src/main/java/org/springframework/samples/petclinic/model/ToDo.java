package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "toDo")

public class ToDo {
    @NotNull
    @NotEmpty
    @Column(name = "title")
    String title;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserTW assignee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "milestone_id")
    @JsonBackReference
    private Milestone milestone;
    
    //Creo que falta tambien una relacion con Tag pero no te lo toco, estoy mas que nada revisando 
}
