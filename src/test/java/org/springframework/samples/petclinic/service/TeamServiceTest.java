package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TeamServiceTest {
    @Autowired
    protected TeamService teamService;
    
    
    @Test
	void shouldFindTeamWithCorrectId() {
		Team team3 = this.teamService.findTeamById(2);
		assertThat(team3.getName().equals("Nike"));
		assertThat(team3.getIdentifier()).isEqualTo("nk");

	}
    @Test
	@Transactional
	public void shouldInsertTeamIntoDatabaseAndGenerateId() {
		

		Team team = new Team();
		team.setName("Ubisoft");
		team.setIdentifier("ub");
		
		teamService.saveTeam(team);
		try {
                this.teamService.saveTeam(team);
            } catch (DataAccessException ex) {
                Logger.getLogger(TeamServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }

		assertThat(team.getId()).isNotNull();
		
	}
    
    @Test
	@Transactional
	void shouldUpdateTeam() {
		Team team = this.teamService.findTeamById(1);
		String oldName = team.getName();
		String newName = oldName + "X";

		team.setName(newName);
		this.teamService.saveTeam(team);

		// retrieving new name from database
		team = this.teamService.findTeamById(1);;
		assertThat(team.getName()).isEqualTo(newName);
	}
    
    @Test
	@Transactional
	void shouldDeleteTeam() {
    	
    	teamService.deleteTeamById(2);
		Team team = this.teamService.findTeamById(2);
		assertThat(team).isNull();
	}
    
}
