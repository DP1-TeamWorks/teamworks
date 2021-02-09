package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.service.AttachmentService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;

public class AttachmentController {

	private final AttachmentService attachmentService;

	@Autowired
	public AttachmentController(AttachmentService attachmentService, MessageService messageService) {
		this.attachmentService = attachmentService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@DeleteMapping(value = "/api/Attachment")
	public ResponseEntity<String> deleteAttachment(@RequestParam(required = true) Integer AttachmentId) {

		try {
			attachmentService.deleteAttatchmentById(AttachmentId);
			return ResponseEntity.ok("Attachment Deleted");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}

}
