package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.repository.ParticipationRepository;
import org.springframework.samples.petclinic.validation.DateIncoherenceException;
import org.springframework.samples.petclinic.validation.ManyProjectManagerException;
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
	public void saveParticipation(Participation participation) throws DataAccessException, ManyProjectManagerException, DateIncoherenceException {
		if(participation.getIsProjectManager()&&participation.getProject().getParticipation().stream().filter(x->x.getIsProjectManager()).findAny().isPresent()) {
			throw new ManyProjectManagerException();
		}/*else if(!participation.getInitialDate().isBefore(participation.getFinalDate())){
			throw new DateIncoherenceException();
		}*/
		else {
			participationRepository.save(participation);
		}
		
	}

	@Transactional
	public void deleteParticipationById(Integer participationId) throws DataAccessException {
		participationRepository.deleteById(participationId);
	}

	@Transactional(readOnly = true)
	public Participation findParticipationById(Integer participationId) throws DataAccessException {
		return participationRepository.findById(participationId);
	}

	@Transactional(readOnly = true)
	public Collection<Participation> findParticipationByUserIdAndProjectId(Integer userId, Integer projectId) throws DataAccessException {
		return participationRepository.findParticipationByUserAndDeparment(userId, projectId);
	}
	@Transactional(readOnly = true)
	public Collection<Participation> findUserParticipations(Integer userId) throws DataAccessException {
		return participationRepository.findUserParticipations(userId);
	}

	@Transactional(readOnly = true)
	public Participation findCurrentParticipation(Integer userId, Integer projectId) throws DataAccessException {
		return participationRepository.findCurrentParticipation(userId, projectId);
	}
	@Transactional(readOnly = true)
	public Collection<Participation> findCurrentParticipationsUser(Integer userId) throws DataAccessException {
		return participationRepository.findCurrentParticipationsUser(userId);
	}

	@Transactional(readOnly = true)
	public Collection<Project> findMyProjects(Integer userId) throws DataAccessException {
		return participationRepository.findMyProjects(userId);
	}

	@Transactional(readOnly = true)
	public Collection<Project> findMyDepartemntProjects(Integer userId, Integer departmentId) throws DataAccessException {
		return participationRepository.findMyDepartemntProjects(userId, departmentId);
	}
}
