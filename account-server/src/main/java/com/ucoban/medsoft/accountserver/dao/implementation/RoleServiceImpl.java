package com.ucoban.medsoft.accountserver.dao.implementation;

import com.ucoban.medsoft.accountserver.dao.service.IRoleService;
import com.ucoban.medsoft.accountserver.entity.Role;
import com.ucoban.medsoft.accountserver.repository.IRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	
	private final IRoleRepository roleRepository;
	
	public RoleServiceImpl(IRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}
	
}
