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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Getter
@Setter
@Entity
@Table(name = "project")
public class Project extends BaseEntity {

	@Column(name = "name")
	@NotNull
	@NotEmpty
	private String name;

	@Column(name = "description")
	@NotNull
	@NotEmpty
	private String description;

	@Column(name = "creation_timestamp")
	@CreationTimestamp
	private LocalDate creationTimestamp;

	@ManyToOne(optional = false)
	@JoinColumn(name = "department_id")
	@JsonBackReference(value="department-project")
	private Department department;
	
	@JsonManagedReference(value="project-milestone")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
	private List<Milestone> milestones;
	
	@JsonManagedReference(value="project-participation")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
	private List<Participation> participation;
}
