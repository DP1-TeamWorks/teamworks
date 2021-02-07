package org.springframework.samples.petclinic.model;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
    // Attributes

    @Column(name = "timestamp")
    @CreationTimestamp
    LocalDate timestamp;

    @Column(name = "subject")
    @Size(min = 0, max = 150)
    private String subject;

    @Column(name = "text")
    @NotNull
    @Size(min = 0, max = 750)
    private String text;

    @Transient
    private List<String> recipientsEmails;

    @Transient
    private List<Integer> recipientsIds;

    @NotNull
    @Column(name = "read")
    private Boolean read;

    // Relations
    @OneToOne(optional = true)
    @JoinColumn(name = "reply_to")
    private Message replyTo;

    @Transient
    private List<Integer> toDoList;

    @Transient
    private List<Integer> tagList;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id")
    private UserTW sender;

    @ManyToMany
    @JoinColumn(name = "recipients")
    private List<UserTW> recipients;

    @Column(name = "attatchments")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "message", orphanRemoval = true)
    private List<Attatchment> attatchments;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tag> tags;

    // TODO once tags work then remove mappedby from here?
    @ManyToMany
    private List<ToDo> toDos;
}
