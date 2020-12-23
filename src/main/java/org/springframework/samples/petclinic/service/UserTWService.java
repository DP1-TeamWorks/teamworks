package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.repository.UserTWRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTWService {
	
	private UserTWRepository userRepository;
	
	
	@Autowired
	public UserTWService(UserTWRepository userRepository) {
		this.userRepository = userRepository;
		
	}
	
	@Transactional 
	public void saveUser(UserTW user) throws DataAccessException {
		//user.setEnabled(true);
		userRepository.save(user);
	}
	
	@Transactional(readOnly = true)
	public UserTW findUserById(Integer userId) {
		return userRepository.findById(userId);
	}
	
	@Transactional(readOnly = true)
	public Collection<UserTW> findUserByName(String name) {
		return userRepository.findByName(name);
	}
	
	@Transactional(readOnly = true)
	public Collection<UserTW> findUserByLastName(String lastname) {
		return userRepository.findByLastName(lastname);
	}
	@Transactional 
	public void deleteUserById(Integer userId) throws DataAccessException {
		userRepository.deleteById(userId);
	}

	@Transactional(readOnly = true)
    public Collection<UserTW> getAllUsers() throws DataAccessException {
        return userRepository.findAll();
    }
	@Transactional(readOnly = true)
	public UserTW getLoginUser(String email,String password) throws DataAccessException {
		UserTW user=userRepository.findbyEmail(email);
		if(user.getPassword().equals(password)) {
			return user;
		}
		else {
			return null;
		}
		
	}
	
	
	

}
