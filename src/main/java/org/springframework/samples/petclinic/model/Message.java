package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "messages")

public class Message extends BaseEntity {
	// Attributes
	@NotNull
	@Column(name = "timestamp")
	@CreationTimestamp
	LocalDate timestamp;

	@NotNull
	@Column(name = "subject")
	private String subject;

	@NotNull
	@Column(name = "text")
	private String text;

	@NotNull
	@Column(name = "read")
	private Boolean read;

	@Transient
	private List<String> recipientsEmail;

	@Transient
	private List<Integer> recipientsIds;

	// Relations
	@OneToOne(optional = true)
	@JoinColumn(name = "reply_to")
	private Message replyTo;

	@ManyToOne(optional = false)
	@JoinColumn(name = "sender_id")
	private UserTW sender;

	@ManyToMany
	@JoinColumn(name = "recipients")
	private List<UserTW> recipients;

	@Column(name = "attachments")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "message")
	private List<Attachment> attachments;

	@Transient
	private List<Integer> tagList;

	@ManyToMany
	private List<Tag> tags;

	@ManyToMany
	private List<ToDo> toDos;

}
