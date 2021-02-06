package org.springframework.samples.petclinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserTW extends BaseEntity {

    public interface StrippedUser {
        Integer getId();

        String getName();

        String getLastname();

        String getEmail();
    }

    // Attributes

    @NotNull
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]+$")
    @NotEmpty
    @Size(min = 1, max = 25)
    @Column(name = "name")
    String name;

    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]+$")
    @NotEmpty
    @Size(min = 1, max = 120)
    @Column(name = "lastname")
    String lastname;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "password")
    String password;

    String profileThumbUrl;

    @Column(name = "joinDate")
    @CreationTimestamp
    LocalDate joinDate;

    @NotNull
    Role role;

    // Relations

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id")
    @JsonBackReference("userteam")
    private Team team;

    @JsonIgnore
    // @JsonManagedReference(value="user-belongs")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userTW", orphanRemoval = true)
    private List<Belongs> belongs;

    @JsonIgnore
    // @JsonManagedReference(value="user-participation")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userTW", orphanRemoval = true)
    private List<Participation> participation;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee")
    private List<ToDo> toDos;
}
