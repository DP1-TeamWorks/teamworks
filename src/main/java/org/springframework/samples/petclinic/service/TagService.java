package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    public void saveTag(Tag tag) throws DataAccessException {
        tagRepository.save(tag);
    }

    @Transactional(readOnly = true)
    public Tag findTagById(Integer tagId) {
        return tagRepository.findById(tagId);
    }

    @Transactional(readOnly = true)
    public void deleteTagById(Integer tagId) throws DataAccessException {
        tagRepository.deleteById(tagId);
    }

}
