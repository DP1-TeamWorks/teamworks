package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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

public class ToDo extends BaseEntity {

    // Attributes

    @NotNull
    @NotEmpty
    @Column(name = "title")
    String title;

    // Relations

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserTW assignee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "milestone_id")
    @JsonBackReference(value="milestone-toDo")
    private Milestone milestone;

    
    @ManyToMany
    private List<Tag> tags;

    // TODO: Message Relation

}
