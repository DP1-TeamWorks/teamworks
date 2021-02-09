package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "todos")

public class ToDo extends BaseEntity {

    // Attributes

    @Size(min = 0, max = 35)
    @Column(name = "title")
    @NotEmpty
    String title;

    @NotNull
    @Column(name = "done")
    Boolean done;

    // Relations

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserTW assignee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "milestone_id")
    @JsonIgnore
    // @JsonBackReference(value="milestone-toDo")
    private Milestone milestone;

    @ManyToMany
    @JsonIgnoreProperties("todos")
    private List<Tag> tags;

    @JsonIgnore
    @ManyToMany(mappedBy = "toDos")
    private List<Message> messages;

    public Integer getAssigneeId() {
        return assignee != null ? assignee.getId() : null;
    }

    public String getAssigneeName() {
        return assignee != null ? assignee.getName() : null;
    }

    public String getAssigneeLastname() {
        return assignee != null ? assignee.getLastname() : null;
    }

    public String getAssigneeEmail() {
        return assignee != null ? assignee.getEmail() : null;
    }

    @Override
    public String toString() {
        return "ToDo [assignee=" + assignee + ", done=" + done + ", messages=" + messages + ", milestone=" + milestone
                + ", tags=" + tags + ", title=" + title + "]";
    }

}
