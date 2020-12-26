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
@Table(name = "tags")

public class Tag extends BaseEntity {

    // Attributes

    @NotNull
    @NotEmpty
    @Column(name = "title")
    String title;

    @NotNull
    @NotEmpty
    @Column(name = "color")
    String color;

    // Relations

    @ManyToOne(optional = false)
    @JoinColumn(name = "projects_id")
    @JsonBackReference
    private Project project;

    @ManyToMany
    private List<Milestone> milestones;

    @ManyToMany
    private List<ToDo> toDos;

    // TODO: Message Relation

}
