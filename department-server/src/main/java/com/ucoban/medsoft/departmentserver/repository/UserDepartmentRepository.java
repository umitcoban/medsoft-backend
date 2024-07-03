package com.ucoban.medsoft.departmentserver.repository;

import com.ucoban.medsoft.departmentserver.entity.Department;
import com.ucoban.medsoft.departmentserver.entity.UserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDepartmentRepository extends JpaRepository<UserDepartment, Long> {

}
