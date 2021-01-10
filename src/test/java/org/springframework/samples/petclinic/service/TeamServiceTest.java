package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TeamServiceTest {
    @Autowired
    protected TeamService teamService;
    
    
    @Test
	void shouldFindTeamWithCorrectId() {
		Team team3 = this.teamService.findTeamById(2);
		assertThat(team3.getName()).startsWith("Nike");
		assertThat(team3.getIdentifier()).isEqualTo("nk");

	}
    @Test
	@Transactional
	public void shouldInsertPetIntoDatabaseAndGenerateId() {
		

		Team team = new Team();
		team.setName("Ubisoft");
		team.setIdentifier("ub");
		
		owner6.addPet(pet);
		assertThat(owner6.getPets().size()).isEqualTo(found + 1);

            try {
                this.petService.savePet(pet);
            } catch (DuplicatedPetNameException ex) {
                Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
            }
		this.ownerService.saveOwner(owner6);

		owner6 = this.ownerService.findOwnerById(6);
		assertThat(owner6.getPets().size()).isEqualTo(found + 1);
		// checks that id has been generated
		assertThat(pet.getId()).isNotNull();
	}
}
