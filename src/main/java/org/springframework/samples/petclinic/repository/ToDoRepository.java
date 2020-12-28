package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.ToDo;

public interface ToDoRepository extends Repository<ToDo, Integer> {
    void save(ToDo toDo) throws DataAccessException;

    void deleteById(Integer id) throws DataAccessException;

    public ToDo findById(Integer id);
}
