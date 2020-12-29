package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Attatchment;
import org.springframework.samples.petclinic.repository.AttatchmentRepository;
import org.springframework.transaction.annotation.Transactional;

public class AttatchmentService {
	private AttatchmentRepository attatchmentRepository;

	@Autowired
	public AttatchmentService(AttatchmentRepository attatchmentRepository) {
		this.attatchmentRepository = attatchmentRepository;
	}

	@Transactional
	public void saveAttatchment(Attatchment attatchment) throws DataAccessException {
		attatchmentRepository.save(attatchment);
	}

	@Transactional(readOnly = true)
	public Attatchment findAttatchmentById(Integer attatchmentId) throws DataAccessException {
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
