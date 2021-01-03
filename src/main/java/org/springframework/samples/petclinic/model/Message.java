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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "userTW")

public class Message extends BaseEntity {
	// Attributes
	@NotNull
	@NotEmpty
	@Column(name = "timeStamp")
	@CreationTimestamp
	LocalDate timeStamp;
		
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "text")
	private String text;
	
	
	//Relations
	/*
	@Column(name = "attatchment")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "message", orphanRemoval = true)
	private List<Attatchment> attatchments;
	*/
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "message")
    @JsonBackReference
    private Message reply;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "user_idsender")
    @JsonBackReference(value = "message-sender")
    private UserTW sender;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "user_idrecipient")
    @JsonBackReference(value = "message-recipient")
    private UserTW recipient;
	
	@ManyToOne(optional = true)
	@JoinColumn(name="messageId")
    @JsonBackReference(value="message-id")
	private Message message;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "message", orphanRemoval = true)
    @JsonManagedReference(value="message-id")
	private List<Message> messages;
	
	//TODO: tag + todo
	
	
}
