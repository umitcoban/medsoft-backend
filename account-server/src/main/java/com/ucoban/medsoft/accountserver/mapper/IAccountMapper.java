package com.ucoban.medsoft.accountserver.mapper;

import com.ucoban.medsoft.accountserver.dao.client.IDocumentFeignClient;
import com.ucoban.medsoft.accountserver.dto.AccountDto;
import com.ucoban.medsoft.accountserver.dto.RegisterDto;
import com.ucoban.medsoft.accountserver.dto.UpdateDto;
import com.ucoban.medsoft.accountserver.entity.Account;
import org.mapstruct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;



@Order(Ordered.HIGHEST_PRECEDENCE)
@Mapper(implementationName = "AccountMapperImpl",
        componentModel = "spring",
        uses = {IDocumentFeignClient.class, IRoleMapper.class, Logger.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)

public interface IAccountMapper {
    Account registerDtoToAccount(RegisterDto registerDto);

    @Mappings({@Mapping(target = "photo", expression = "java(getPhoto(account.getId(), documentFeignClient))")})
    AccountDto accountDtoToAccount(Account account, @Context IDocumentFeignClient documentFeignClient);

    Account updateDtoToAccount(UpdateDto updateDto);

    List<AccountDto> accountListToAccountDtoList(List<Account> accountList);

    default String getPhoto(String userId, IDocumentFeignClient documentFeignClient) {
        try {
            var response = documentFeignClient.getUserProfilPhoto(userId).getBody();
            return response.data() != null ? response.data() : null;
        }catch (Exception ex){
            System.out.println("getPhoto client error: "+  ex.getMessage());
            return null;
        }

    }

}
