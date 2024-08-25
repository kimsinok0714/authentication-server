package com.mzc.authentication_server.util;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mzc.authentication_server.dto.AccountDto;

public class JWTUtil {

    // JWT Token을 생성한다.
    public static String generate(AccountDto accountDto) {
        Date now = new Date();
        return JWT.create()
            .withSubject(accountDto.getAccountId())               // 토큰명 : 토큰의 주체(토큰이 누구에 관한 것인지 나타내는 정보)
            .withExpiresAt(DateUtils.addSeconds(now, 10))  // 토큰 유효기간
            .withIssuedAt(now)                                    // 토큰 발행일
            .sign(Algorithm.HMAC512("secretKey"));         // 토큰 서명 : HMAC512 해시 알고리즘과 secretKey라는 비밀 키를 사용하여 토큰을 서명합니다
    }
}
