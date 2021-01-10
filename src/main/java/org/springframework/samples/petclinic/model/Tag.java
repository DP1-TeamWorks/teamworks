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
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "project_id")
    @JsonBackReference(value = "project-tag")
    private Project project;

    @JsonIgnore
    // @JsonBackReference(value="milestone-tag")
    @ManyToMany
    private List<Milestone> milestones;

    @JsonIgnore
    @ManyToMany
    private List<ToDo> todos;

    @JsonIgnore
    @ManyToMany
    private List<Message> messages;

}
