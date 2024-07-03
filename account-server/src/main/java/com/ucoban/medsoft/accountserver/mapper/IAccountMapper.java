package com.ucoban.medsoft.accountserver.mapper;

import com.ucoban.medsoft.accountserver.dto.AccountDto;
import com.ucoban.medsoft.accountserver.dto.RegisterDto;
import com.ucoban.medsoft.accountserver.dto.UpdateDto;
import com.ucoban.medsoft.accountserver.entity.Account;
import com.ucoban.medsoft.accountserver.util.AccountMapperHelper;
import org.mapstruct.*;
import org.slf4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;


@Order(Ordered.HIGHEST_PRECEDENCE)
@Mapper(implementationName = "AccountMapperImpl",
		componentModel = "spring",
		uses = {IRoleMapper.class, Logger.class},
		injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IAccountMapper {
	
	Account registerDtoToAccount(RegisterDto registerDto);
	
	@Mappings({
			@Mapping(target = "photo", expression = "java(accountMapperHelper.getPhoto(account.getId()))"),
			@Mapping(target = "departments", expression = "java(accountMapperHelper.getDepartments(account.getId()))")
	})
	AccountDto accountDtoToAccount(Account account, @Context AccountMapperHelper accountMapperHelper);
	
	Account updateDtoToAccount(UpdateDto updateDto);
	
	List<AccountDto> accountListToAccountDtoList(List<Account> accountList);
	
	@AfterMapping
	default void injectServices(@MappingTarget AccountDto accountDto, @Context AccountMapperHelper accountMapperHelper) {
		accountDto.setPhoto(accountMapperHelper.getPhoto(accountDto.getId()));
		accountDto.setDepartments(accountMapperHelper.getDepartments(accountDto.getId()));
	}
}
