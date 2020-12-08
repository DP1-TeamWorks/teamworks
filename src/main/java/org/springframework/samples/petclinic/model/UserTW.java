package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
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
	@Column(name = "email")
	String email;
	
	@NotNull
	@NotEmpty
	@Column(name = "password")
	String password;
	
	
	
	String profileThumbUrl;
	
	//@NotNull
	//@NotEmpty
	@Column(name= "joinDate")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	LocalDate joinDate;
	
	@NotNull
	Role role;

}
