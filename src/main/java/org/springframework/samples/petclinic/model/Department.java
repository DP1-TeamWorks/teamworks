package org.springframework.samples.petclinic.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department extends BaseEntity {
	@NotNull
	@NotEmpty
	@Column(name = "name", unique = true)
	private @Getter @Setter String name;
	@NotNull
	@NotEmpty
	@Column(name = "description")
	private @Getter @Setter String description;

	@ManyToOne(optional = false)
	@JoinColumn(name = "team_id")
	@JsonBackReference
	private @Getter @Setter Team team;
	@Column(name = "projects")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "department", orphanRemoval = true)
	private @Getter @Setter List<Project> projects;

}
