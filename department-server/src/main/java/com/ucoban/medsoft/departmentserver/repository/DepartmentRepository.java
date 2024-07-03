package com.ucoban.medsoft.departmentserver.repository;

import com.ucoban.medsoft.departmentserver.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository  extends JpaRepository<Department, Long> {
	
	Optional<Department> findByName(String name);
	
	@Query("SELECT d FROM Department d JOIN d.userDepartments ud WHERE ud.userId = ?1")
	List<Department> findByUserDepartmentsUserId(String userId);
}
