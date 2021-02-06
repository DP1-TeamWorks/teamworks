package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tags")

public class Tag extends BaseEntity {

    // Attributes

    @NotEmpty
    @Size(min = 1, max = 200)
    @Column(name = "title")
    String title;

   
    @Pattern(regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$")
    @NotBlank
    @Column(name = "color")
    String color;

    // Relations
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id")
    @JsonBackReference(value = "project-tag")
    private Project project;

    @JsonIgnore
    @ManyToMany
    private List<Milestone> milestones;

    @JsonIgnore
    @ManyToMany
    private List<ToDo> todos;

    @JsonIgnore
    @ManyToMany
    private List<Message> messages;

}
