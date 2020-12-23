package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.model.UserTW;

public interface ToDoRepository extends Repository<ToDo, Integer> {
    void save(ToDo toDo) throws DataAccessException;

    void deleteById(Integer id) throws DataAccessException;

    public ToDo findById(Integer id);

    @Query("SELECT u.assignee FROM toDos u WHERE u.toDoId = :toDoId")
    public UserTW findAsignee(@Param("toDoId") Integer toDoId);
}
