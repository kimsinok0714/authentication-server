package com.mzc.authentication_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mzc.authentication_server.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}
