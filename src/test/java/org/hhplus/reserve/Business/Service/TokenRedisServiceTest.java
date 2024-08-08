package org.hhplus.reserve.Business.Service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TokenRedisServiceTest {

    @Autowired
    private TokenRedisService tokenRedisService;

    String userId = "10422";

    @Test
    @DisplayName("토큰 저장")
    void saveToken() {
        tokenRedisService.saveToken(userId);
    }

    @Test
    @DisplayName("토큰 검증 성공")
    void checkTokenSUCESS(){
        tokenRedisService.saveToken(userId);
        boolean check = tokenRedisService.checkToken(userId);
        assertTrue(check);
    }

    @Test
    @DisplayName("토큰 검증 - 실패 - CASE : 존재하지않는 토큰")
    void checkTokenFAIL(){
        tokenRedisService.saveToken(userId);
        boolean check = tokenRedisService.checkToken(userId);
        assertFalse(check);
    }


    @Test
    @DisplayName("토큰 활성화 - 성공")
    void activeTokenSUCESS() {
        tokenRedisService.saveToken(userId);
        tokenRedisService.activeToken(userId);
    }

    @Test
    @DisplayName("토큰 활성화 - 실패 - CASE : DB에 토큰 정보 없음")
    void activeTokenFAIL1() {
        tokenRedisService.activeToken(userId);
    }

    @Test
    @DisplayName("토큰 활성화 검증 - 성공")
    void validateTokenSUCESS() {
        tokenRedisService.saveToken(userId);
        tokenRedisService.activeToken(userId);
        boolean validateActive = tokenRedisService.validateActiveToken(userId);
        assertTrue(validateActive);
    }

    @Test
    @DisplayName("토큰 비활성화 - 성공")
    void deactivateTokenSUCESS() {
        tokenRedisService.saveToken(userId);
        tokenRedisService.activeToken(userId);
        tokenRedisService.deactivateToken(userId);
        boolean validateActive = tokenRedisService.validateActiveToken(userId);
        assertFalse(validateActive);
    }
}