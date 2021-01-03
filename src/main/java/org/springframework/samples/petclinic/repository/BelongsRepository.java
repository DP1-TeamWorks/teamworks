package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Belongs;

public interface BelongsRepository extends Repository<Belongs, Integer>{
	void save(Belongs belongs) throws DataAccessException;
	void deleteById(Integer belongsId) throws DataAccessException;
	Belongs findById(Integer  belongsId) throws DataAccessException;
	@Query(value="SELECT u FROM Belongs u WHERE u.userTW.id = :userId and u.department.id= :departmentId" )
	public Belongs findBelongByUserAndDeparment(@Param("userId") Integer userId,@Param("departmentId") Integer departmentId);
	@Query(value="SELECT u FROM Belongs u WHERE u.userTW.id = :userId and u.department.id= :departmentId and u.finalDate=null" )
    public Belongs findCurrentBelongs(@Param("userId") Integer userId,@Param("departmentId") Integer departmentId);
}
