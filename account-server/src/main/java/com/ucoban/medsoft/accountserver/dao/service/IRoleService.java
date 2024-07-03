package com.ucoban.medsoft.accountserver.dao.service;

import com.ucoban.medsoft.accountserver.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public interface IRoleService {
	List<Role> getAllRoles();
}
