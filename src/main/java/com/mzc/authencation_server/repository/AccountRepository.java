package com.mzc.authencation_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mzc.authencation_server.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}
