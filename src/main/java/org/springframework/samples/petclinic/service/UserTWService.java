package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CleanupFailureDataAccessException;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.enums.Role;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.repository.UserTWRepository;
import org.springframework.samples.petclinic.validation.ManyTeamOwnerException;
import org.springframework.samples.petclinic.validation.ToDoMaxToDosPerAssigneeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTWService {

	private UserTWRepository userRepository;
	private MessageService messageService;
	private ToDoService toDoService;

	@Autowired
	public UserTWService(UserTWRepository userRepository, MessageService messageService, ToDoService todoService) {
		this.userRepository = userRepository;
		this.messageService = messageService;
		this.toDoService = todoService;
	}

	@Transactional(rollbackFor = ManyTeamOwnerException.class)
	public void saveUser(UserTW user) throws DataAccessException, ManyTeamOwnerException {
		// user.setEnabled(true);
        UserTW teamOwner = findTeamOwner(user.getTeam().getId());
		if (user.getTeam().getUsers() != null && user.getRole().equals(Role.team_owner) && !user.equals(teamOwner)) {
			throw new ManyTeamOwnerException();
		} else {
			userRepository.save(user);
		}

	}

	@Transactional(readOnly = true)
	public UserTW findUserById(Integer userId) {
		return userRepository.findById(userId);
	}

    @Transactional(readOnly = true)
    public UserTW findTeamOwner(Integer teamId) {
        return userRepository.findTeamOwner(teamId);
    }

	@Transactional(readOnly = true)
	public UserTW findByEmail(String userEmail) {
		return userRepository.findByEmail(userEmail);
	}

	@Transactional(readOnly = true)
	public Collection<UserTW> findUserByName(String name) {
		return userRepository.findByName(name);
	}

	@Transactional(readOnly = true)
	public Collection<UserTW> findUsersByTeam(Integer teamId) {

	    return userRepository.findUsersByTeam(teamId);
	}

    @Transactional(readOnly = true)
    public Collection<UserTW> findUserByLastName(String lastname) {
        return userRepository.findByLastName(lastname);
    }

	public void deleteUserById(Integer userId) throws DataAccessException {

        UserTW user = userRepository.findById(userId);
        if (user == null)
            return;

        for (Message m : user.getMessagesSent())
        {
            m.setSender(null);
            messageService.saveMessage(m);
        }

        for (Message m : user.getMessagesReceived())
        {
            m.getRecipients().remove(user);
            messageService.saveMessage(m);
        }

        for (ToDo t : user.getAssignedTodos())
        {
            t.setAssignee(null);
            try {
                toDoService.saveToDo(t);
            } catch (ToDoMaxToDosPerAssigneeException e) {
                throw new CleanupFailureDataAccessException("error deleting", e);
            }
        }

		userRepository.deleteById(userId);
	}

	@Transactional(readOnly = true)
	public Collection<UserTW> getAllUsers() throws DataAccessException {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public UserTW getLoginUser(String email, String password) throws DataAccessException {
		UserTW user = userRepository.findByEmail(email);
		if (user != null && SecurityConfiguration.passwordEncoder().matches(password, user.getPassword())) {
			return user;
		} else {
			return null;
		}

	}

}
