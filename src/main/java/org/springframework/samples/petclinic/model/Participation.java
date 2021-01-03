package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
@Table(name = "participation")

public class Participation extends BaseEntity {

	@NotNull
	@NotEmpty
	@Column(name = "initialDate")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate initialDate;

	@NotNull
	@NotEmpty
	@Column(name = "finalDate")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate finalDate;

	@NotNull
	@NotEmpty
	@Column(name = "isProjectManager")
	private Boolean isProjectManager;

	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "userId")
	@JsonBackReference(value="user-participation")
	UserTW userTW;

	@ManyToOne
	@MapsId("projectId")
	@JoinColumn(name = "projectId")
	@JsonBackReference(value="project-participation")
	Project project;

}
