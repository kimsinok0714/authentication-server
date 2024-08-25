package com.mzc.authencation_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mzc.authencation_server.domain.Account;
import com.mzc.authencation_server.dto.AccountDto;
import com.mzc.authencation_server.dto.ResponseDto;
import com.mzc.authencation_server.service.AccountService;
import com.mzc.authencation_server.util.JWTUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(value = "api/v1/accounts")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AccountService accountService;



    @PostMapping(value = "/join")
    public ResponseEntity<ResponseDto> join(@Valid @RequestBody AccountDto accountDto) throws Exception {

        ResponseDto.ResponseDtoBuilder  responseBuilder = ResponseDto.builder();

        Account account = accountService.retrieveAccount(accountDto);
        
        if (account != null) {
            responseBuilder.code("100").message("already join user.");
        } else {
            accountService.createAccount(accountDto, null);
            responseBuilder.code("200").message("success");
        }
        
        log.debug("join.accont.id = {}", accountDto.getAccountId());

        return ResponseEntity.ok(responseBuilder.build());
        
    }


    
    @PostMapping(value = "/token")
    public ResponseEntity<ResponseDto> token(@Valid @RequestBody AccountDto accountDto) throws Exception {
        ResponseDto.ResponseDtoBuilder responseBuilder = ResponseDto.builder();

        Account account = accountService.retrieveAccount(accountDto);
        if (account == null) {
            responseBuilder.code("101").message("Unknown user");
        } else {
            String token = getToken(accountDto);
            accountService.createAccount(accountDto, token);
            responseBuilder.code("200").message("success");
            responseBuilder.token(token);
        }
        
        log.debug("token.accont.id = {}", accountDto.getAccountId());

        return ResponseEntity.ok(responseBuilder.build());
        
    }


    // Token 생성
    private String getToken(AccountDto accountDto) {
        return JWTUtil.generate(accountDto);
    }



}
