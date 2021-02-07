package org.springframework.samples.petclinic.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Attachment;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.service.AttachmentService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class AttachmentController {

	private final AttachmentService attachmentService;
	private final MessageService messageService;

	@Autowired
	public AttachmentController(AttachmentService attachmentService, MessageService messageService) {
		this.attachmentService = attachmentService;
		this.messageService = messageService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@PostMapping(value = "/api/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> newAttachment(HttpServletRequest r, @Valid @RequestBody Attachment attach,
			@RequestParam Integer messageId, BeanPropertyBindingResult errors) {
		try {
			if (errors.hasErrors())
				return ResponseEntity.badRequest().body(errors.getAllErrors().toString());

			Message msg = messageService.findMessageById(messageId);
			attach.setMessage(msg);

			attachmentService.uploadAndSaveAttachment(attach);

			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (DataAccessException | IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
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
