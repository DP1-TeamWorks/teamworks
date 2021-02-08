package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Attachment;
import org.springframework.samples.petclinic.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AttachmentService {
	private AttachmentRepository attatchmentRepository;

	@Autowired
	public AttachmentService(AttachmentRepository attatchmentRepository) {
		this.attatchmentRepository = attatchmentRepository;
	}

	@Transactional(rollbackFor = IOException.class)
	public void uploadAndSaveAttachment(Attachment attatchment) throws DataAccessException, IOException {
		String fileName = attatchment.getFile().getOriginalFilename();
		Path path = Paths.get("src//main//webapp//resources//upload//" + fileName);

		byte[] bytes = attatchment.getFile().getBytes();
		Files.write(path, bytes);
		log.info("Uploading file");
		attatchment.setUrl("/upload/" + fileName);
		attatchmentRepository.save(attatchment);
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
