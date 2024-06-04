package com.ucoban.medsoft.accountserver.dao.service;

import com.ucoban.medsoft.accountserver.dto.AccountAnalyticsDto;
import com.ucoban.medsoft.accountserver.dto.RegisterDto;
import com.ucoban.medsoft.accountserver.dto.UpdateDto;
import com.ucoban.medsoft.accountserver.entity.Account;
import java.util.List;

public interface IAccountService {
     Account findById(String id);
     List<Account> findAll();
     void create(RegisterDto registerDto);
     void delete(String id);
     Account update(UpdateDto updateDto,String userId);
     AccountAnalyticsDto findAccountsAnalyticsDto();

}
