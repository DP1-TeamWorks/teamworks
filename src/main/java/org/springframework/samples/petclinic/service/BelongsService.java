package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.repository.BelongsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BelongsService {
	private BelongsRepository belongsRepository;

	@Autowired
	public BelongsService(BelongsRepository belongsRepository) {
		this.belongsRepository = belongsRepository;
	}

	@Transactional
	public void saveBelongs(Belongs belongs) throws DataAccessException {
		belongsRepository.save(belongs);
	}

	@Transactional
	public void deleteBelongsById(Integer belongsId) throws DataAccessException {
		belongsRepository.deleteById(belongsId);
	}
	@Transactional(readOnly = true)
	public Belongs findBelongsById(Integer belongsId) throws DataAccessException {
		return belongsRepository.findById(belongsId);
	}
	@Transactional(readOnly = true)
	public Collection<Belongs> findBelongByUserIdAndDepartmentId(Integer userId, Integer departmentId) {
		return belongsRepository.findBelongByUserAndDeparment(userId, departmentId);
	}
	@Transactional(readOnly = true)
	public Belongs findCurrentBelongs(Integer userId, Integer departmentId) {
		return belongsRepository.findCurrentBelongs(userId, departmentId);
	}
	@Transactional(readOnly = true)
	public Collection<Department> findMyDepartments(Integer userId){
		return belongsRepository.findMyDepartments(userId);
	}

}
