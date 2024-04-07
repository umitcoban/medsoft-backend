package com.ucoban.medsoft.accountserver.mapper;

import com.ucoban.medsoft.accountserver.annotation.RoleToDto;
import com.ucoban.medsoft.accountserver.dto.RoleDto;
import com.ucoban.medsoft.accountserver.entity.Role;
import org.mapstruct.Mapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


@Mapper(implementationName = "RoleMapperImpl", componentModel = "spring")
@Order(Ordered.HIGHEST_PRECEDENCE)
public interface IRoleMapper {

    @RoleToDto
    RoleDto roleToDto(Role role);

}
