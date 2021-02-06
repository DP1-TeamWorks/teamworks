package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.TagService;
import org.springframework.samples.petclinic.service.ToDoService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class MessageController {

    private final MessageService messageService;
    private final UserTWService userService;
    private final TagService tagService;
    private final ToDoService toDoService;

    @Autowired
    public MessageController(MessageService messageService, UserTWService userService, TagService tagService,
            ToDoService toDoService) {
        this.messageService = messageService;
        this.userService = userService;
        this.tagService = tagService;
        this.toDoService = toDoService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping(value = "/api/message/inbox")
    public List<Message> getMyInboxMessages(HttpServletRequest r) {
        try {
            log.info("Getting inbox messages");
            Integer userId = (Integer) r.getSession().getAttribute("userId");
            UserTW user = userService.findUserById(userId);
            List<Message> messageList = (messageService.findMessagesByUserId(user)).stream()
                    .collect(Collectors.toList());
            log.info("Succeed");
            return messageList;
        } catch (DataAccessException d) {
            log.error("ERROR: " + d.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't get inbox");
        }
    }

    @GetMapping(value = "/api/message/sent")
    public List<Message> getMySentMessages(HttpServletRequest r) {
        try {
            log.info("Getting sent messages");
            Integer userId = (Integer) r.getSession().getAttribute("userId");
            List<Message> messageList = (messageService.findMessagesSentByUserId(userId)).stream()
                    .collect(Collectors.toList());
            log.info("Succeed");
            return messageList;
        } catch (DataAccessException d) {
            log.error("ERROR: " + d.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages");
        }
    }

    @GetMapping(value = "/api/message/byTag")
    public List<Message> getMessagesByTag(HttpServletRequest r, @RequestParam(required = true) int tagId) {
        try {
            log.info("Getting messages by tag with id: " + tagId);
            Integer userId = (Integer) r.getSession().getAttribute("userId");
            Tag tag = tagService.findTagById(tagId);
            UserTW user = userService.findUserById(userId);
            List<Message> messageList = (messageService.findMessagesByTag(user, tag)).stream()
                    .collect(Collectors.toList());
            log.info("Succeed");
            return messageList;
        } catch (DataAccessException d) {
            log.error("ERROR: " + d.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages");
        }
    }

    @GetMapping(value = "/api/message/bySearch")
    public List<Message> getMessagesBySearch(HttpServletRequest r, @RequestParam(required = true) String search) {
        try {
            log.info("Searching for messages, keywords: " + search);
            Integer userId = (Integer) r.getSession().getAttribute("userId");
            UserTW user = userService.findUserById(userId);
            List<Message> messageList = (messageService.findMessagesBySearch(user, search.toLowerCase())).stream()
                    .collect(Collectors.toList());
            log.info("Succeed");
            return messageList;
        } catch (DataAccessException d) {
            log.error("ERROR: " + d.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages" + d);
        }
    }

    @GetMapping(value = "/api/message/noRead")
    public Long getNumberOfNotReadMessages(HttpServletRequest r) {
        try {
            log.info("Getting number of not read messages");
            Integer userId = (Integer) r.getSession().getAttribute("userId");
            UserTW user = userService.findUserById(userId);
            Long notReadMessages = (messageService.findMessagesByUserId(user)).stream().filter(m -> !m.getRead())
                    .count();

            log.info("Succeed");
            return notReadMessages;
        } catch (DataAccessException d) {
            log.error("ERROR: " + d.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages");
        }
    }

    @GetMapping(value = "/api/message/noReadByTag")
    public Long getNumberOfNotReadMessagesByTag(HttpServletRequest r, @RequestParam(required = true) int tagId) {
        try {
            log.info("Getting number of not read messages by tag");
            Integer userId = (Integer) r.getSession().getAttribute("userId");
            Tag tag = tagService.findTagById(tagId);
            UserTW user = userService.findUserById(userId);
            Long notReadMessages = (messageService.findMessagesByTag(user, tag)).stream().filter(m -> !m.getRead())
                    .count();

            log.info("Succeed");
            return notReadMessages;
        } catch (DataAccessException d) {
            log.error("ERROR: " + d.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages");
        }
    }

    @PostMapping(value = "api/message")
    public ResponseEntity<String> newMessage(HttpServletRequest r, @Valid @RequestBody Message message) {
        try {
            log.info("Creating new message");
            Integer userId = (Integer) r.getSession().getAttribute("userId");
            UserTW sender = userService.findUserById(userId);
            message.setSender(sender);
            message.setRead(false);

            log.info("Setting the recipient list");
            List<UserTW> recipientList = message.getRecipientsEmails().stream()
                    .map(mail -> userService.findByEmail(mail)).collect(Collectors.toList());
            message.setRecipients(recipientList);

            log.info("Setting the todo list");
            log.info("ToDoList: " + message.getToDoList().toString());
            List<ToDo> toDoList = message.getToDoList().stream().map(toDoId -> toDoService.findToDoById(toDoId))
                    .collect(Collectors.toList());
            log.info("ToDoList: " + toDoList.toString());
            message.setToDos(toDoList);

            log.info("Setting the tag list");
            List<Tag> tagList = message.getTagList().stream().map(tagId -> tagService.findTagById(tagId))
                    .collect(Collectors.toList());
            message.setTags(tagList);

            log.info("Saving the message");
            messageService.saveMessage(message);
            return ResponseEntity.ok().build();
        } catch (DataAccessException d) {
            log.error("ERROR: " + d.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "api/message/reply")
    public ResponseEntity<String> replyMessage(HttpServletRequest r,
            @Valid @RequestParam(required = true) Message message, @RequestParam(required = true) Integer replyId) {

        log.info("Replying to the message with id: " + replyId);
        Message repliedMessage = messageService.findMessageById(replyId);
        message.setReplyTo(repliedMessage);
        return newMessage(r, message);

    }

    @PostMapping(value = "api/message/forward")
    public ResponseEntity<String> forwardMessage(HttpServletRequest r,
            @RequestParam(required = true) List<String> forwardList, Integer messageId) {
        log.info("Forwarding the message with id: " + messageId + " to: " + forwardList);
        Message message = messageService.findMessageById(messageId);
        message.setRecipientsEmails(forwardList);
        return newMessage(r, message);
    }

    @PostMapping(value = "/api/message/markAsRead")
    public ResponseEntity<String> markAsRead(HttpServletRequest r, @RequestParam(required = true) Integer messageId) {
        try {
            log.info("Marking a message as read");
            Integer userId = (Integer) r.getSession().getAttribute("userId");
            UserTW user = userService.findUserById(userId);
            Message message = messageService.findMessageById(messageId);
            if (message.getRecipients().contains(user)) {
                message.setRead(true);

                log.info("Succeed, saving message");
                messageService.saveMessage(message);
            } else {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().build();
        } catch (DataAccessException d) {
            return ResponseEntity.badRequest().build();
        }
    }
}
