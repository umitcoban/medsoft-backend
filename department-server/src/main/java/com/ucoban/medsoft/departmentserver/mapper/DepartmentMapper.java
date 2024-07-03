package com.ucoban.medsoft.departmentserver.mapper;


import com.ucoban.medsoft.departmentserver.dto.DepartmentDto;
import com.ucoban.medsoft.departmentserver.entity.Department;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", implementationName = "DepartmentMapperImpl")
public interface DepartmentMapper {
	
	@InheritInverseConfiguration
	DepartmentDto departmentToDepartmentDto(Department department);
	
	List<DepartmentDto> departmentToDepartmentDtoList(List<Department> departments);
	
	Department departmentDtoToDepartment(DepartmentDto departmentDto);
}
