package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.repository.BelongsRepository;
import org.springframework.transaction.annotation.Transactional;

public class BelongsService {
	private BelongsRepository belongsRepository;
	@Autowired
	public BelongsService(BelongsRepository belongsRepository) {
		this.belongsRepository=belongsRepository;
	}

	@Transactional
	public void saveBelongs(Belongs belongs) throws DataAccessException {
		belongsRepository.save(belongs);
	}
	@Transactional
	public void deleteBelongsById(Integer belongsId) throws DataAccessException {
		belongsRepository.deleteById(belongsId);
	}

}
