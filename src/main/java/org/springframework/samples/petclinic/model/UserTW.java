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
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "userTW")


public class UserTW extends BaseEntity {
	
	@NotNull
	@NotEmpty
	@Column(name = "name")
	String name;
	
	@NotNull
	@NotEmpty
	@Column(name = "lastname")
	String lastname;
	
	@NotNull
	@NotEmpty
	@Column(name = "email",unique = true)
	String email;
	
	@NotNull
	@NotEmpty
	@Column(name = "password")
	String password;
	
	String profileThumbUrl;
	
	@Column(name= "joinDate")
	@CreationTimestamp
	LocalDate joinDate;
	
	@NotNull
	Role role;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "team_id")
	@JsonBackReference
	private Team team;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userTW", orphanRemoval = true)
	private List<Belongs> belongs;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userTW", orphanRemoval = true)
	private List<Participation> participation;
}
