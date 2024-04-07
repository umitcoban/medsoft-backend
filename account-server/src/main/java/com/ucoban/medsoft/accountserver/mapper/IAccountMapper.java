package com.ucoban.medsoft.accountserver.mapper;

import com.ucoban.medsoft.accountserver.dto.AccountDto;
import com.ucoban.medsoft.accountserver.dto.RegisterDto;
import com.ucoban.medsoft.accountserver.dto.UpdateDto;
import com.ucoban.medsoft.accountserver.entity.Account;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;



import java.util.List;

@Mapper(implementationName = "AccountMapperImpl",
        componentModel = "spring",
        uses = {IRoleMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IAccountMapper {
    Account registerDtoToAccount(RegisterDto registerDto);

    AccountDto accountDtoToAccount(Account account);

    Account updateDtoToAccount(UpdateDto updateDto);

    List<AccountDto> accountListToAccountDtoList(List<Account> accountList);

}
