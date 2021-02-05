package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.UserTW;

public interface BelongsRepository extends Repository<Belongs, Integer> {
	void save(Belongs belongs) throws DataAccessException;

	void deleteById(Integer belongsId) throws DataAccessException;

	Belongs findById(Integer belongsId) throws DataAccessException;

	@Query(value = "SELECT u FROM Belongs u WHERE u.userTW.id = :userId and u.department.id= :departmentId")
	public Collection<Belongs> findBelongByUserAndDeparment(@Param("userId") Integer userId,
			@Param("departmentId") Integer departmentId);

	@Query(value = "SELECT u FROM Belongs u WHERE u.userTW.id = :userId")
	public Collection<Belongs> findUserBelongs(@Param("userId") Integer userId);

	@Query(value = "SELECT u FROM Belongs u WHERE u.userTW.id = :userId and u.department.id= :departmentId and u.finalDate=null")
	public Belongs findCurrentBelongs(@Param("userId") Integer userId, @Param("departmentId") Integer departmentId);

	@Query(value = "SELECT u FROM Belongs u WHERE u.userTW.id = :userId and u.finalDate=null")
	public Collection<Belongs> findCurrentUserBelongs(@Param("userId") Integer userId);

	@Query(value = "SELECT u.department FROM Belongs u WHERE u.userTW.id = :userId and u.finalDate=null")
	public Collection<Department> findMyDepartments(@Param("userId") Integer userId);

	@Query(value = "SELECT b FROM Belongs b WHERE b.department.id= :departmentId and b.finalDate=null and b.isDepartmentManager = true")
	public Belongs findCurrentDepartmentManager(@Param("departmentId") Integer departmentId);

	@Query(value = "SELECT b FROM Belongs b WHERE b.department.id= :departmentId and b.finalDate=null")
    public Collection<Belongs> findCurrentBelongsInDepartment(@Param("departmentId") Integer departmentId);

}
