package com.mzc.authencation_server.service;

import java.util.Optional;

import org.springframework.stereotype.Service;


import com.mzc.authencation_server.domain.Account;
import com.mzc.authencation_server.dto.AccountDto;
import com.mzc.authencation_server.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;


    /*
     * 회원 가입을 요청하거나 토큰 발급을 요청하는 경우 회원이 존재여부를 확인하기 위해서 호출된다.
     */

    // 계정 조회
    public Account retrieveAccount(AccountDto accountDto) {
        Optional<Account>  optional = accountRepository.findById(accountDto.getAccountId());        
        if (optional.isPresent()) {
            Account account = optional.get();
            if (account.getPassword().equals(accountDto.getPassword())) {
                return account;
            }
        }
        return null;
    }


    /*
     * 아이디와 비밀번호 요청이 있는 경우 Account 테이블에 정보가 등록되고,
     * 요청받은 아이디와 비밀번호가 이미 존재하는 경우 Account 테이블에 정보가 업데이트된다.
     * 회원 가입을 요청하는 경우 token은 null 값으로 처리한다.
     * 토큰 발급 요청을 한 경우 토큰을 생성하고  Account 테이블에서 토큰 값만 업데이트한다.
     */
    
    // 계정 정보 저장
    public void createAccount(AccountDto accountDto, String token) {
        accountRepository.save(Account.builder()
                        .accountId(accountDto.getAccountId())
                        .password(accountDto.getPassword())
                        .token(accountDto.getToken())
                        .build());              
    }


}
