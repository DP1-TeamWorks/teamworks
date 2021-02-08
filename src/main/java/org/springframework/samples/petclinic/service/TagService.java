package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CleanupFailureDataAccessException;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.repository.TagRepository;
import org.springframework.samples.petclinic.validation.TagLimitProjectException;
import org.springframework.samples.petclinic.validation.ToDoMaxToDosPerAssigneeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {

    private TagRepository tagRepository;
    private final ToDoService toDoService;
    private final MessageService messageService;

    @Autowired
    public TagService(TagRepository tagRepository, ToDoService toDoService, MessageService messageService) {
        this.tagRepository = tagRepository;
        this.toDoService = toDoService;
        this.messageService = messageService;
    }

    @Transactional
    public void saveTag(Tag tag) throws DataAccessException, TagLimitProjectException {
    	if(tag.getProject().getTags().size() > 5) {
    		throw new TagLimitProjectException();
    	}
    	else {
    		tagRepository.save(tag);
    	}

    }

    @Transactional(readOnly = true)
    public Tag findTagById(Integer tagId) throws DataAccessException {
        return tagRepository.findById(tagId);
    }

    @Transactional(readOnly = true)
    public Tag findTagByName(String tagName) throws DataAccessException {
        return tagRepository.findByName(tagName);
    }

    @Transactional
    public void deleteTagById(Integer tagId) throws DataAccessException {
        Tag tag = tagRepository.findById(tagId);
        if (tag == null)
            return;

        for (ToDo t : tag.getTodos())
        {
            t.getTags().remove(tag);
            try {
                toDoService.saveToDo(t); // pointless try catch
            } catch (ToDoMaxToDosPerAssigneeException e) {
                throw new CleanupFailureDataAccessException("error deleting todo",e);
            }
        }

        for (Message t : tag.getMessages())
        {
            t.getTags().remove(tag);
            messageService.saveMessage(t);
        }

        tagRepository.deleteById(tagId);
    }
}
