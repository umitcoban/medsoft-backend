package com.ucoban.medsoft.accountserver.dao.service;

import com.ucoban.medsoft.accountserver.dto.RegisterDto;
import com.ucoban.medsoft.accountserver.dto.UpdateDto;
import com.ucoban.medsoft.accountserver.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAccountService {
     Account findById(String id);
     List<Account> findAll();
     void create(RegisterDto registerDto);
     void delete(String id);
     Account update(UpdateDto updateDto,String userId);
}