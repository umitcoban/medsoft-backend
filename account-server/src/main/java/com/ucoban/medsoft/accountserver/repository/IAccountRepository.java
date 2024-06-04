package com.ucoban.medsoft.accountserver.repository;


import com.ucoban.medsoft.accountserver.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {

    @Query(value = "select * from get_accounts_with_roles_count()", nativeQuery = true)
    Map<String, Integer> findAccountRoleAndCounts();

    @Query(value = "select count(*), DATE(created_at) as date from accounts group by DATE(created_at)", nativeQuery = true)
    List<Object[]> findAccountCountWithDate();

}
