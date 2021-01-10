package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.samples.petclinic.model.UserTW;
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
        assertThat(belongsTest.getIsDepartmentManager());
    }

    @Test
    void shouldFindCurrentBelongsByUserIdAndDepartmentId() {
        Belongs belongs = this.belongService.findCurrentBelong(2, 1);
        assertThat(belongs.getDepartment().getName()).isEqualTo("Calidad");
        assertThat(belongs.getUserTW().getName()).isEqualTo("Julia");
    }

    @Test
    void shouldFindUserBelongsByUserId() {
        List<Belongs> userBelongs = this.belongService.findUserBelongs(2).stream().collect(Collectors.toList());
        assertThat(userBelongs.size()).isEqualTo(3);
    }

    @Test
    void shouldFindUserCurrentBelongsByUserId() {
        List<Belongs> userBelongs = this.belongService.findCurrentBelongsUser(2).stream().collect(Collectors.toList());
        assertThat(userBelongs.size()).isEqualTo(3);
    }

    @Test
    void shouldFindMyDepartments() {
        List<Department> userBelongs = this.belongService.findMyDepartments(2).stream().collect(Collectors.toList());
        assertThat(userBelongs.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void shouldInsertBelongsIntoDatabaseAndGenerateId() {
        UserTW user = userTWService.findUserById(2);
        Department dpt = departmentService.findDepartmentById(1);
        Belongs belongs = new Belongs();
        belongs.setDepartment(dpt);
        belongs.setUserTW(user);
        user.getBelongs().add(belongs);
        dpt.getBelongs().add(belongs);
        try {
            this.belongService.saveBelongs(belongs);
            this.departmentService.saveDepartment(dpt);
            this.userTWService.saveUser(user);
        } catch (DataAccessException ex) {
            Logger.getLogger(BelongsServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertThat(belongs.getId()).isNotNull();
        assertThat(belongs.getUserTW().getId()).isEqualTo(user.getId());
        assertThat(belongs.getDepartment().getId()).isEqualTo(dpt.getId());

    }

    @Test
    @Transactional
    void shouldUpdateAndFinishBelongs() {
        Belongs belongs = this.belongService.findBelongsById(1);
        LocalDate ld = LocalDate.now();
        belongs.setFinalDate(ld);
        this.belongService.saveBelongs(belongs);

        Belongs updatedBelongs = this.belongService.findBelongsById(1);
        assertThat(updatedBelongs.getFinalDate()).isEqualTo(ld);
    }

    @Test
    @Transactional
    void shouldDeleteBelongs() {
        belongService.deleteBelongsById(2);
        Belongs team = this.belongService.findBelongsById(2);
        assertThat(team).isNull();
    }

}
