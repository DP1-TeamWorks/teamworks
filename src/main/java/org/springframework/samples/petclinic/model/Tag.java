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
@Table(name = "tags")

public class Tag {
    @NotNull
    @NotEmpty
    @Column(name = "title")
    String title;

    @NotNull
    @NotEmpty
    @Column(name = "color")
    String color;

    // Relaciones

    @ManyToOne(optional = false)
    @JoinColumn(name = "projects_id")
    @JsonBackReference
    private Project project;
}
