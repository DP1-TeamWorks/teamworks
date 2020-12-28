package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.repository.ToDoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ToDoService {

    private ToDoRepository toDoRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Transactional
    public void saveToDo(ToDo toDo) throws DataAccessException {
        toDoRepository.save(toDo);
    }

    @Transactional(readOnly = true)
    public ToDo findTeamById(Integer toDoId) {
        return toDoRepository.findById(toDoId);
    }

    @Transactional(readOnly = true)
    public void deleteTeamById(Integer toDoId) throws DataAccessException {
        toDoRepository.deleteById(toDoId);
    }

}
