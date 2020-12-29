package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;

public class Attatchment extends BaseEntity {
	
	@NotNull
	@NotEmpty
	@Column(name = "url")
	private String url;
	
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "message")
	@JsonBackReference
	private Message message;

}
