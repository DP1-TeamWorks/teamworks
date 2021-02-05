package org.springframework.samples.petclinic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
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
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    private String subject;

    @Column(name = "text")
    private String text;

    @NotNull
    @Column(name = "read")
    private Boolean read;

    @Transient
    private List<String> recipientsEmails;

    @Transient
    private List<Integer> toDoList;

    @Transient
    private List<Integer> tagList;

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

    @Column(name = "attatchments")
    @OneToMany(
        cascade = CascadeType.ALL,
        mappedBy = "message",
        orphanRemoval = true
    )
    private List<Attatchment> attatchments;

    @ManyToMany
    private List<Tag> tags;

    @ManyToMany
    private List<ToDo> toDos;
}
