package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attachment")
public class Attachment extends BaseEntity {
	
	// Attributes
	
	@NotNull
	@NotEmpty
	@Column(name = "url")
	private String url;
	
	// Relations
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "message_id")
	@JsonIgnore
	private Message message;
	
}
