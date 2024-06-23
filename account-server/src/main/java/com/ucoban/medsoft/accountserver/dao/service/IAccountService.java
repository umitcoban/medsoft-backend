package com.ucoban.medsoft.accountserver.dao.service;

import com.ucoban.medsoft.accountserver.dto.*;
import com.ucoban.medsoft.accountserver.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IAccountService {
     Account findById(String id);
     List<Account> findAll();
     void create(RegisterDto registerDto);
     void delete(String id);
     Account update(UpdateDto updateDto,String userId);
     AccountAnalyticsDto findAccountsAnalyticsDto();
     Page<AccountDto> findAll(Pageable pageable);
     List<AccountDto> findAll(Sort sort);
     void updateAccountRole(UpdateRoleDto updateRoleDto, String userId);
     boolean changePassword(String userId, String newPassword);
}
