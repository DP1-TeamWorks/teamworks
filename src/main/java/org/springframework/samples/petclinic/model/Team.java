package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "teams")


public class Team {
	
	@NotNull
	@NotEmpty
	@Column(name = "name")
	String name;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@NotEmpty
	@Column(name = "identifier")
	String id;

}
