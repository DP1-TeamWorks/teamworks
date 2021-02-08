package org.springframework.samples.petclinic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;

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
    @Size(min = 0, max = 100)
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
    @JsonIgnore
    private UserTW sender;

    public UserTW.StrippedUser getStrippedSender() {
        return new StrippedUserImpl(sender);
    }

    @ManyToMany
    @JoinColumn(name = "recipients")
    @JsonIgnore
    private List<UserTW> recipients;

    public List<UserTW.StrippedUser> getStrippedRecipients() {
        return recipients.stream().map(user -> new StrippedUserImpl(user)).collect(Collectors.toList());
    }

    @Transient
    private MultipartFile file;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "message", orphanRemoval = true)
    private List<Attachment> attatchments;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tag> tags;

    @ManyToMany
    private List<ToDo> toDos;
}
