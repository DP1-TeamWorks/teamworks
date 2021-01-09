package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.ToDo;

public interface ToDoRepository extends Repository<ToDo, Integer> {
    void save(ToDo toDo) throws DataAccessException;

    void deleteById(Integer id) throws DataAccessException;

    public ToDo findById(Integer id);

    @Query(value = "SELECT u FROM ToDo u WHERE u.assignee.id = :userId and u.milestone.id = :milestoneId")
    public Collection<ToDo> findToDoByMilestoneAndUser(@Param("milestoneId") Integer milestoneId,
            @Param("userId") Integer userId);
}
