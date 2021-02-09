package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.*;
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
@Table(name = "tags", uniqueConstraints = @UniqueConstraint(columnNames = { "title", "project_id" }))
public class Tag extends BaseEntity {

    // Attributes

    @NotEmpty
    @Size(min = 1, max = 15)
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
    @ManyToMany(mappedBy = "tags")
    private List<ToDo> todos;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private List<Message> messages;

    public Integer getTodosUsingTag() {
        return todos.size();
    }

}
