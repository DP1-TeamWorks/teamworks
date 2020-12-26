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
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "milestones")

public class Milestone extends BaseEntity {

	@NotNull
	@NotEmpty
	@Column(name = "name", unique = true)
	private String name;

	@NotNull
	@NotEmpty
	@Column(name = "dueFor")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate dueFor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "projects_id")
	@JsonBackReference
	private Project project;

	@Column(name = "toDos")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "milestone", orphanRemoval = true)
	private List<ToDo> toDos;

}
