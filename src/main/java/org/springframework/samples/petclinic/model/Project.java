package org.springframework.samples.petclinic.model;

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
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project extends BaseEntity {
	
	  // Attributes

	@Column(name = "name")
	@NotNull
	@NotEmpty
	private String name;

	@Column(name = "description")
	@NotNull
	@NotEmpty
	private String description;

	@Column(name = "creationTimestamp")
	@CreationTimestamp
	private LocalDate creationTimestamp;
	
	// Relations

	@ManyToOne(optional = false)
	@JoinColumn(name = "department_id")
	@JsonIgnore
	// @JsonBackReference(value="department-project")
	private Department department;

	@JsonIgnore
	// @JsonManagedReference(value="project-milestone")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
	private List<Milestone> milestones;

	@JsonIgnore
	// @JsonManagedReference(value="project-participation")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
	private List<Participation> participations;

	@JsonManagedReference(value = "project-tag")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
	private List<Tag> tags;
}
