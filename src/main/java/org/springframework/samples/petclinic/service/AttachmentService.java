package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Attachment;
import org.springframework.samples.petclinic.repository.AttachmentRepository;
import org.springframework.transaction.annotation.Transactional;

public class AttachmentService {
	private AttachmentRepository attatchmentRepository;

	@Autowired
	public AttachmentService(AttachmentRepository attatchmentRepository) {
		this.attatchmentRepository = attatchmentRepository;
	}

	@Transactional(rollbackFor = IOException.class)
	public void uploadAndSaveAttachment(Attachment attatchment) throws DataAccessException, IOException {
		String fileName = attatchment.getFile().getOriginalFilename();
		Path path = Paths.get("src//main//resources//static//upload");
		String route = path.toFile().getAbsolutePath();

		byte[] bytes = attatchment.getFile().getBytes();
		Path finalRoute = Paths.get(route + "//" + fileName);
		Files.write(finalRoute, bytes);

		attatchment.setUrl(finalRoute.toString());
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
	 * @Transactional(readOnly = true) public Collection<Attatchment>
	 * getAllAttatchment() throws DataAccessException { return
	 * attatchmentRepository.findAll(); }
	 */
}
