package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Attachment;
import org.springframework.samples.petclinic.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttachmentService {
	private AttachmentRepository attatchmentRepository;

	@Autowired
	public AttachmentService(AttachmentRepository attatchmentRepository) {
		this.attatchmentRepository = attatchmentRepository;
	}

	@Transactional
	public void saveAttatchment(Attachment attatchment) throws DataAccessException {
		attatchmentRepository.save(attatchment);
	}

	@Transactional(readOnly = true)
	public Attachment findAttatchmentById(Integer attatchmentId) throws DataAccessException {
		return attatchmentRepository.findById(attatchmentId);
	}

	@Transactional
	public void deleteAttatchmentById(Integer attatchmentId) throws DataAccessException {
		attatchmentRepository.deleteById(attatchmentId);
	}
	
	/*
	@Transactional(readOnly = true)
	public Collection<Attatchment> getAllAttatchment() throws DataAccessException {
		return attatchmentRepository.findAll();
	}
	*/
}
