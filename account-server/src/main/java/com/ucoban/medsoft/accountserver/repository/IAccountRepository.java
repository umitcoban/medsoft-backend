package com.ucoban.medsoft.accountserver.repository;

import com.ucoban.medsoft.accountserver.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {

}
