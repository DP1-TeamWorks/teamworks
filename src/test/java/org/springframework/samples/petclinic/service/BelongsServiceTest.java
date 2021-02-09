package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.validation.DateIncoherenceException;
import org.springframework.samples.petclinic.validation.ManyDepartmentManagerException;
import org.springframework.samples.petclinic.validation.ManyTeamOwnerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BelongsServiceTest {
    @Autowired
    protected BelongsService belongService;
    @Autowired
    protected UserTWService userTWService;
    @Autowired
    protected DepartmentService departmentService;

    @Test
    void shouldFindBelongsWithCorrectId() {
        Belongs belongsTest = this.belongService.findBelongsById(1);
        assertThat(belongsTest.getDepartment().getName()).isEqualTo("Calidad");
        assertThat(belongsTest.getIsDepartmentManager()).isTrue();
    }

   
    @Test
    void shouldfindBelongByUserIdAndDepartmentId() {
        List<Belongs> userBelongs = this.belongService.findBelongByUserIdAndDepartmentId(2, 1).stream().collect(Collectors.toList());
        assertThat(userBelongs.size()).isEqualTo(1);
    }
    @Test
    void shouldFindUserBelongsByUserId() {
        List<Belongs> userBelongs = this.belongService.findUserBelongs(2).stream().collect(Collectors.toList());
        assertThat(userBelongs.size()).isEqualTo(3);
    }
    @Test
    void shouldFindCurrentBelongsByUserIdAndDepartmentId() {
        Belongs belongs = this.belongService.findCurrentBelongs(2, 1);
        assertThat(belongs.getDepartment().getName()).isEqualTo("Calidad");
        assertThat(belongs.getUserTW().getName()).isEqualTo("Julia");
    }
    
    @Test
    void shouldFindCurrentUserBelongsByUserId() {
        List<Belongs> userBelongs = this.belongService.findCurrentUserBelongs(2).stream().collect(Collectors.toList());
        assertThat(userBelongs.size()).isEqualTo(3);
    }
    @Test
    void shouldFindMyDepartmentsByDepartmentId() {
        List<Department> departments = this.belongService.findMyDepartments(2).stream().collect(Collectors.toList());
        assertThat(departments.size()).isEqualTo(3);
    }
   
    @Test
    void shouldFindCurrentDepartmentManagerByDepartmentId() {
        Belongs belong=this.belongService.findCurrentDepartmentManager(1);
        assertThat(belong.getUserTW().getName()).isEqualTo("Julia");
        assertThat(belong.getUserTW().getLastname()).isEqualTo("Fabra");
    } 
    @Test
    void shouldFindCurrentBelongsInDepartment() {
    	 List<Belongs> userBelongs=this.belongService.findCurrentBelongsInDepartment(1).stream().collect(Collectors.toList());
    	 assertThat(userBelongs.size()).isEqualTo(2);
    }
    

    @Test
    @Transactional
    public void shouldInsertBelongsIntoDatabaseAndGenerateId() {
        UserTW user = userTWService.findUserById(2);
        Department dpt = departmentService.findDepartmentById(1);
        Belongs belongs = new Belongs();
        belongs.setDepartment(dpt);
        belongs.setInitialDate(LocalDate.now());
        belongs.setUserTW(user);
        belongs.setIsDepartmentManager(false);
        user.getBelongs().add(belongs);
        dpt.getBelongs().add(belongs);
        try {
        	this.departmentService.saveDepartment(dpt);
            this.userTWService.saveUser(user);
            this.belongService.saveBelongs(belongs);
            
        } catch (DataAccessException | ManyDepartmentManagerException | DateIncoherenceException | ManyTeamOwnerException ex) {
            Logger.getLogger(ParticipationServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertThat(belongs.getId()).isNotNull();
        assertThat(belongs.getUserTW().getId()).isEqualTo(user.getId());
        assertThat(belongs.getDepartment().getId()).isEqualTo(dpt.getId());
        assertThat(dpt.getBelongs().contains(belongs)).isTrue();
        assertThat(user.getBelongs().contains(belongs)).isTrue();
    }
    @Test
	@Transactional
	public void shouldNotInsertABelongsNullIntoDatabase() {

    	Belongs belongs = new Belongs();
		assertThrows(Exception.class, ()-> {
			this.belongService.saveBelongs(belongs);
            });

	}

    @Test
    @Transactional
    void shouldUpdateAndFinishBelongs() {
        Belongs belongs = this.belongService.findBelongsById(1);
        LocalDate ld = LocalDate.now();
        belongs.setFinalDate(ld);
        try {
			this.belongService.saveBelongs(belongs);
		} catch (DataAccessException | ManyDepartmentManagerException | DateIncoherenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Belongs updatedBelongs = this.belongService.findBelongsById(1);
        assertThat(updatedBelongs.getFinalDate()).isEqualTo(ld);
    }

    @Test
    @Transactional
    void shouldDeleteBelongs() {
        belongService.deleteBelongsById(2);
        Belongs belongs = this.belongService.findBelongsById(2);
        assertThat(belongs).isNull();
    }

}
