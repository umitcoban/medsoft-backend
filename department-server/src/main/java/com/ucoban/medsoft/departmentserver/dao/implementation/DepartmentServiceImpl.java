package com.ucoban.medsoft.departmentserver.dao.implementation;

import com.ucoban.medsoft.departmentserver.dao.DepartmentService;
import com.ucoban.medsoft.departmentserver.dto.AssignDepartmentDto;
import com.ucoban.medsoft.departmentserver.entity.Department;
import com.ucoban.medsoft.departmentserver.entity.UserDepartment;
import com.ucoban.medsoft.departmentserver.repository.DepartmentRepository;
import com.ucoban.medsoft.departmentserver.repository.UserDepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	private final DepartmentRepository departmentRepository;
	
	private final UserDepartmentRepository departmentUserRepository;
	
	@Override
	public Department getDepartmentById(long id) {
		return departmentRepository.findById(id).orElseThrow();
	}
	
	@Override
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}
	
	@Override
	public Department getDepartmentByName(String name) {
		return departmentRepository.findByName(name).orElseThrow();
	}
	
	@Override
	public Department addDepartment(Department department) {
		return departmentRepository.save(department);
	}
	
	@Override
	public Department updateDepartment(Department department) {
		return departmentRepository.save(department);
	}
	
	@Override
	public void deleteDepartment(long id) {
		departmentRepository.deleteById(id);
	}
	
	@Override
	public void assignDepartment(AssignDepartmentDto assignDepartmentDto) {
		var department = getDepartmentById(assignDepartmentDto.departmentId());
		var userDepartment = new UserDepartment();
		userDepartment.setDepartment(department);
		userDepartment.setUserId(assignDepartmentDto.userId());
		departmentUserRepository.save(userDepartment);
	}
	
	@Override
	public List<Department> getDepartmentsByUserId(String userId) {
		return departmentRepository.findByUserDepartmentsUserId(userId);
	}
}
