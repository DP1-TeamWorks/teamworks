package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;

import lombok.Data;
@Data
@Entity
@Table(name = "projects")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	protected Integer id;
	@Column(name = "name",unique = true)
	@NotNull
	@NotEmpty
	private String name;
	@Column(name = "description") 
	@NotNull
	@NotEmpty
	private String description;
	@Column(name = "creation_timestamp")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	@NotEmpty
	private LocalDate creationTimestamp;
	
	@ManyToOne(optional=false )
	@JoinColumn(name = "deparments")
	@JsonBackReference
	private Department deparment;
}
