package com.ucoban.medsoft.departmentserver.mapper;

import com.ucoban.medsoft.departmentserver.dto.AssignDepartmentDto;
import com.ucoban.medsoft.departmentserver.entity.UserDepartment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "UserDepartmentMapperImpl")
public interface UserDepartmentMapper {
	
	UserDepartment toUserDepartment(AssignDepartmentDto assignDepartmentDto);
	
}
