package org.hhplus.reserve.Business.Service.IntegrationTest;

import org.hhplus.reserve.Business.Domain.User.TokenRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TokenRedisServiceIntegrationTest {

    @Autowired
    private TokenRedisService tokenRedisService;

    @BeforeEach
    void setUp() {
    }
    @Test
    @DisplayName("토큰 저장 - 성공")
    void saveTokenSUCESS() {
        UUID userUuid = UUID.randomUUID();
        tokenRedisService.saveToken(userUuid.toString());
    }
    @Test
    @DisplayName("토큰 저장 - 실패 _ UUID 가 아닐경우")
    void saveTokenFAIL1() {
        String failValue = "fail";
        tokenRedisService.saveToken(failValue);
    }

    @Test
    @DisplayName("토큰 저장 - 실패 _ null 값")
    void saveTokenFAIL2() {
        String nullValue = null;
        tokenRedisService.saveToken(nullValue);
    }

    @Test
    @DisplayName("토큰 검증 - 성공") // 다시 해보기. 자꾸 fail남
    void checkTokenSUCESS(){
        UUID userUuid = UUID.randomUUID();
        tokenRedisService.saveToken(userUuid.toString());
        boolean check = tokenRedisService.checkToken(userUuid.toString());
        assertTrue(check);
    }

    @Test
    @DisplayName("토큰 검증 - 실패 - CASE : 존재하지않는 토큰")
    void checkTokenFAIL(){
        UUID userUuid = UUID.randomUUID();
        UUID userUuidFail = UUID.randomUUID();
        tokenRedisService.saveToken(userUuid.toString());
        boolean check = tokenRedisService.checkToken(userUuidFail.toString());
        assertFalse(check);
    }


    @Test
    @DisplayName("토큰 활성화 - 성공")
    void activeTokenSUCESS() {
        UUID userUuid = UUID.randomUUID();
        tokenRedisService.activeToken(userUuid.toString());
     }


    @Test
    @DisplayName("토큰 활성화 검증 - 성공")
    void validateTokenSUCESS() {
        UUID userUuid = UUID.randomUUID();
        tokenRedisService.saveToken(userUuid.toString());
        tokenRedisService.activeToken(userUuid.toString());
        boolean validateActive = tokenRedisService.validateActiveToken(userUuid.toString());
        assertTrue(validateActive);
    }

    @Test
    @DisplayName("토큰 활성화 검증 - 실패 - 활성화되지 않은 토큰")
    void validateTokenFAIL() {
        UUID userUuid = UUID.randomUUID();
        tokenRedisService.saveToken(userUuid.toString());
        //tokenRedisService.activeToken(userUuid.toString());
        boolean validateActive = tokenRedisService.validateActiveToken(userUuid.toString());
        assertTrue(validateActive);
    }

    @Test
    @DisplayName("토큰 비활성화 - 성공")
    void deactivateTokenSUCESS() {
        UUID userUuid = UUID.randomUUID();
        tokenRedisService.saveToken(userUuid.toString());
        tokenRedisService.activeToken(userUuid.toString());
        tokenRedisService.deactivateToken(userUuid.toString());
        boolean validateActive = tokenRedisService.validateActiveToken(userUuid.toString());
        assertFalse(validateActive);
    }

    @Test
    @DisplayName("토큰 비활성화 - 실패 - 활성화되고 있는 토큰")
    void deactivateTokenFAIL() {
        UUID userUuid = UUID.randomUUID();
        tokenRedisService.saveToken(userUuid.toString());
        tokenRedisService.activeToken(userUuid.toString());
        //tokenRedisService.deactivateToken(userUuid.toString());
        boolean validateActive = tokenRedisService.validateActiveToken(userUuid.toString());
        assertFalse(validateActive);
    }
}