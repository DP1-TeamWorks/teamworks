package org.springframework.samples.petclinic.model;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "teams")


public class Team extends BaseEntity{
	
	@NotNull
	@NotEmpty
	@Column(name = "name")
	String name;
	
	@NotNull
	@NotEmpty
	@Column(name = "identifier")
	String identifier;
	
	@Column(name = "users")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team", orphanRemoval = true)
	private List<UserTW> users;
	@Column(name = "departments")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team", orphanRemoval = true)
	private List<Department> departments;
	
	
	
	

}
