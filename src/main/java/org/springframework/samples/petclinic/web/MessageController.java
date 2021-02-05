package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

@RestController
public class MessageController {
	private final MessageService messageService;
	private final UserTWService userService;
	private final TagService tagService;
	private final ToDoService toDoService;

	@Autowired
	public MessageController(MessageService messageService, UserTWService userService, TagService tagService, ToDoService toDoService) {
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
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			UserTW user = userService.findUserById(userId);
			List<Message> messageList = (messageService.findMessagesByUserId(user)).stream()
					.collect(Collectors.toList());
			return messageList;
		} catch (DataAccessException d) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't get inbox");

		}
	}

	@GetMapping(value = "/api/message/sent")
	public List<Message> getMySentMessages(HttpServletRequest r) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			List<Message> messageList = (messageService.findMessagesSentByUserId(userId)).stream()
					.collect(Collectors.toList());
			return messageList;
		} catch (DataAccessException d) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages");
		}
	}

	@GetMapping(value = "/api/message/byTag")
	public List<Message> getMessagesByTag(HttpServletRequest r, @RequestParam(required = true) int tagId) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			Tag tag = tagService.findTagById(tagId);
			UserTW user = userService.findUserById(userId);
			List<Message> messageList = (messageService.findMessagesByTag(user, tag)).stream()
					.collect(Collectors.toList());
			return messageList;
		} catch (DataAccessException d) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages");
		}
	}

	@GetMapping(value = "/api/message/bySearch")
	public List<Message> getMessagesBySearch(HttpServletRequest r, @RequestParam(required = true) String search) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			UserTW user = userService.findUserById(userId);
			List<Message> messageList = (messageService.findMessagesBySearch(user, search.toLowerCase())).stream()
					.collect(Collectors.toList());
			return messageList;
		} catch (DataAccessException d) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages" + d);
		}
	}

	@GetMapping(value = "/api/message/noRead")
	public Long getNumberOfNotReadMessages(HttpServletRequest r) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			UserTW user = userService.findUserById(userId);
			Long notReadMessages = (messageService.findMessagesByUserId(user)).stream().filter(m -> !m.getRead())
					.count();
			return notReadMessages;
		} catch (DataAccessException d) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages");
		}
	}

	@GetMapping(value = "/api/message/noReadByTag")
	public Long getNumberOfNotReadMessagesByTag(HttpServletRequest r, @RequestParam(required = true) int tagId) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			Tag tag = tagService.findTagById(tagId);
			UserTW user = userService.findUserById(userId);
			Long notReadMessages = (messageService.findMessagesByTag(user, tag)).stream().filter(m -> !m.getRead())
					.count();
			return notReadMessages;
		} catch (DataAccessException d) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages");
		}
	}
	
	//@Valid delante de requestbody
	@PostMapping(value = "api/message/new")
	public ResponseEntity<String> newMessage(HttpServletRequest r, @Valid @RequestBody Message message) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			UserTW sender = userService.findUserById(userId);
			message.setSender(sender);
			
			message.setRead(false);
			
			List<UserTW> recipientList = new ArrayList<>();
			for (int i = 0; i < message.getRecipientsEmails().size(); i++) {
				UserTW recipient = userService.findByEmail(message.getRecipientsEmails().get(i));
				recipientList.add(recipient);
			}
			message.setRecipients(recipientList);
			
			// TODO set tags in message
			List<Tag> tagList = new ArrayList<>();
			for (int i = 0; i < message.getListOfTags().size(); i++) {
				//FIND TAG BY NAME
				Tag tag = tagService.findTagByName(message.getListOfTags().get(i));
				tagList.add(tag);
			}
			if(tagList!= null) message.setTags(tagList);
			
			// TODO set todos in message
			List<ToDo> toDoList = new ArrayList<>();
			for (int i = 0; i < message.getListOfToDos().size(); i++) {
				//FIND ToDo BY NAME
				//ToDo toDo = toDoService.findToDoByName(message.getListOfToDos().get(i));
				//toDoList.add(toDo);
			}			
			if(toDoList!= null) message.setToDos(toDoList);

			messageService.saveMessage(message);
			return ResponseEntity.ok().build();

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping(value = "api/message/reply")
	public ResponseEntity<String> replyMessage(HttpServletRequest r,
			@Valid @RequestParam(required = true) Message message) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			UserTW sender = userService.findUserById(userId);
			message.setSender(sender);
			message.setRead(false);

			List<UserTW> recipientList = new ArrayList<>();
			for (int i = 0; i < message.getRecipientsIds().size(); i++) {
				UserTW recipient = userService.findUserById(message.getRecipientsIds().get(i));
				recipientList.add(recipient);
			}
			message.setRecipients(recipientList);
			message.setReplyTo(message);
			// TODO set tags in message

			messageService.saveMessage(message);
			return ResponseEntity.ok().build();

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping(value = "api/message/forward")
	public ResponseEntity<String> forwardMessage(HttpServletRequest r,
			@RequestParam(required = true) List<Integer> forwardList, Integer MessageId) {
		try {
			Message message = messageService.findMessageById(MessageId);
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			UserTW sender = userService.findUserById(userId);
			message.setSender(sender);

			List<UserTW> userList = new ArrayList<>();
			for (int i = 0; i < forwardList.size(); i++) {
				UserTW user = userService.findUserById(forwardList.get(i));
				userList.add(user);
			}
			message.setRecipients(userList);
			message.setReplyTo(message);

			// TODO set tags in message
			return ResponseEntity.ok().build();

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping(value = "/api/message/markAsRead")
	public ResponseEntity<String> markAsRead(HttpServletRequest r, @RequestParam(required = true) Integer messageId) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			UserTW user = userService.findUserById(userId);
			Message message = messageService.findMessageById(messageId);
			if (message.getRecipients().contains(user)) {
				message.setRead(true);
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
