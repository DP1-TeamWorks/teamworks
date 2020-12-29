package org.springframework.samples.petclinic.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.repository.ParticipationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParticipationService {
	private ParticipationRepository participationRepository;

	@Autowired
	public ParticipationService(ParticipationRepository participationRepository) {
		this.participationRepository = participationRepository;
	}

	@Transactional
	public void saveParticipation(Participation participation) throws DataAccessException {
		participationRepository.save(participation);
	}

	@Transactional
	public void deleteParticipationById(Integer participationId) throws DataAccessException {
		participationRepository.deleteById(participationId);
	}

	public Participation findParticipationById(Integer participationId) throws DataAccessException {
		return participationRepository.findById(participationId);
	}

	public Participation findParticipationByUserIdAndProjectId(Integer userId, Integer projectId) {
		return new Participation();
	}

}
