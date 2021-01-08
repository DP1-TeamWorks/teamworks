package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {
	private MessageRepository messageRepository;

	@Autowired
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Transactional
	public void saveMessage(Message message) throws DataAccessException {
		messageRepository.save(message);
	}

	@Transactional(readOnly = true)
	public Message findMessageById(Integer messageId) throws DataAccessException {
		return messageRepository.findById(messageId);
	}
	
	@Transactional
	public Collection<Message> findMessagesByUserId(Integer userId) throws DataAccessException {
		return messageRepository.findMessagesByUserId(userId);
	}
	
	@Transactional
	public Collection<Message> findMessagesSentByUserId(Integer userId) throws DataAccessException {
		return messageRepository.findMessagesSentByUserId(userId);
	}

	@Transactional
	public void deleteMessageById(Integer messageId) throws DataAccessException {
		messageRepository.deleteById(messageId);
	}
	
	/*
	@Transactional(readOnly = true)
	public Collection<Message> getAllMessages() throws DataAccessException {
		return messageRepository.findAll();
	}
	*/
}
