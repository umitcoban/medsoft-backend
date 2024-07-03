package com.ucoban.medsoft.departmentserver.dao;

import com.ucoban.medsoft.departmentserver.dto.AssignDepartmentDto;
import com.ucoban.medsoft.departmentserver.entity.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentService")
public interface DepartmentService {
	Department getDepartmentById(long id);
	List<Department> getAllDepartments();
	Department getDepartmentByName(String name);
	Department addDepartment(Department department);
	Department updateDepartment(Department department);
	void deleteDepartment(long id);
	void assignDepartment(AssignDepartmentDto assignDepartmentDto);
	List<Department> getDepartmentsByUserId(String userId);
}
