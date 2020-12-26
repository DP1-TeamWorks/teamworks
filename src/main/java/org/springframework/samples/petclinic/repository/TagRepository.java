package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

public interface TagRepository extends Repository<Tag, Integer> {
    void save(Tag tag) throws DataAccessException;

    void deleteById(Integer id) throws DataAccessException;

    public Tag findById(Integer id);
}