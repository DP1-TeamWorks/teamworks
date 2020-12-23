package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.Tag;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.UserTW;

public interface TagRepository extends Repository<Tag, Integer> {
    void save(Tag tag) throws DataAccessException;

    void deleteById(Integer id) throws DataAccessException;

    public Tag findById(Integer id);

    @Query("SELECT u.project FROM tags u WHERE u.tag_id = :tagId")
    public UserTW findAsignee(@Param("tagId") Integer tagId);

}