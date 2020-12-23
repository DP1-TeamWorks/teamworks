package org.springframework.samples.petclinic.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Data;
@Data
@Entity
@Table(name = "department")
public class Department extends BaseEntity {
	@NotNull
	@NotEmpty
	@Column(name = "name",unique = true)
	private String name;
	@NotNull
	@NotEmpty
	@Column(name = "description")
	private String description;
	
	@Column(name = "projects")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "department", orphanRemoval = true)
	private List<Project> projects;
}
