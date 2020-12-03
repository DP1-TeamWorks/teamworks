package org.springframework.samples.petclinic.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Data;
@Data
@Entity
@Table(name = "departments")
public class Department {
	@Id
	@NotNull
	@NotEmpty
	private String name;
	@NotNull
	@NotEmpty
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "departments", orphanRemoval = true)
	List<Project> projects;
}
