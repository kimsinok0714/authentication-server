package com.mzc.authentication_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mzc.authentication_server.domain.Account;
import com.mzc.authentication_server.dto.AccountDto;
import com.mzc.authentication_server.dto.ResponseDto;
import com.mzc.authentication_server.service.AccountService;
import com.mzc.authentication_server.util.JWTUtil;

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

    /**
     * 회원 가입 요청 api
     * @param accountDto
     * @return
     * @throws Exception
     */

    @PostMapping(value = "/join")
    public ResponseEntity<ResponseDto> join(@Valid @RequestBody AccountDto accountDto) throws Exception {

        ResponseDto.ResponseDtoBuilder  responseBuilder = ResponseDto.builder();

        Account account = accountService.retrieveAccount(accountDto);
        
        if (account != null) {
            responseBuilder.code("100").message("already join user.");
        } else {
            // 회원 등록 처리
            accountService.createAccount(accountDto, null);

            responseBuilder.code("200").message("success");
        }
        
        log.debug("join.accont.id = {}", accountDto.getAccountId());

        return ResponseEntity.ok(responseBuilder.build());
        
    }


    /**
     *      
     * 토큰 발급 요청 api
     * @param accountDto
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/token")
    public ResponseEntity<ResponseDto> token(@Valid @RequestBody AccountDto accountDto) throws Exception {
        ResponseDto.ResponseDtoBuilder responseBuilder = ResponseDto.builder();

        Account account = accountService.retrieveAccount(accountDto);
        if (account == null) {
            // 가입된 회원이 아닌 경우 토큰 발급을 요청한 경우 에러 코드를 반환한다.
            responseBuilder.code("101").message("Unknown user");
        } else {
            // 토큰울 생성한다.
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
