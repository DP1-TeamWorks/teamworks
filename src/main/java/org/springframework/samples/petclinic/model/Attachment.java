package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attachments")
public class Attachment extends BaseEntity {

	// Attributes

	@Column(name = "url")
	private String url;

	@Transient
	@NotNull
	private MultipartFile file;

	// Relations
	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "message")
	private Message message;

}
