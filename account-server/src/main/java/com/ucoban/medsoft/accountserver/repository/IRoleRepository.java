package com.ucoban.medsoft.accountserver.repository;

import com.ucoban.medsoft.accountserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
}
