package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamService {

	private TeamRepository teamRepository;
    private UserTWService userTWService;
    private DepartmentService departmentService;

	@Autowired
	public TeamService(TeamRepository teamRepository, UserTWService userTWService, DepartmentService departmentService) {
		this.teamRepository = teamRepository;
		this.userTWService = userTWService;
		this.departmentService = departmentService;
	}

	@Transactional
	public void saveTeam(Team team) throws DataAccessException {
		teamRepository.save(team);
	}

	@Transactional(readOnly = true)
	public Team findTeamById(Integer teamId) {
		return teamRepository.findById(teamId);
	}

	@Transactional
	public void deleteTeamById(Integer teamId) throws DataAccessException {
	    Team t = findTeamById(teamId);
	    if (t == null)
	        return;
	    for (UserTW u : t.getUsers())
        {
            userTWService.deleteUserById(u.getId());
        }
	    for (Department d : t.getDepartments()) {
            departmentService.deleteDepartmentById(d.getId());
        }
		teamRepository.deleteById(teamId);
	}

	@Transactional(readOnly = true)
	public Collection<Team> getAllTeams() throws DataAccessException {
		return teamRepository.findAll();
	}

}
