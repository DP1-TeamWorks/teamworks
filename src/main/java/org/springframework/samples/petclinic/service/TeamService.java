package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamService {
	
	private TeamRepository teamRepository;
	
	@Autowired
	public TeamService(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}
	
	
	@Transactional 
	public void saveTeam(Team team) throws DataAccessException {
		teamRepository.save(team);
	}
	
	@Transactional(readOnly = true)
	public Team findTeamById(Integer teamId) {
		return teamRepository.findById(teamId);
	}

	@Transactional(readOnly = true)
	public Collection<Team> findTeamByName(String name) {
		return teamRepository.findByName(name);
	}
	
	public void deleteTeamById(Integer teamId) throws DataAccessException {
		teamRepository.deleteById(teamId);
	}
	
	@Transactional(readOnly = true)
    public Collection<Team> getAllTeams() throws DataAccessException {
        return teamRepository.findAll();
    }
}
