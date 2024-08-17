package org.hhplus.reserve.Business.Service.IntegrationTest;

import org.hhplus.reserve.Business.Domain.User.TokenServiceImpl;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
class TokenServiceIntegrationTest { // 토큰 서비스 통합 테스트 - AuthInterceptor 테스트로 변경 예정
    @Autowired
    private TokenServiceImpl tokenService;

    @Test
    @DisplayName("토큰 등록 서비스 - 성공")
    void ApplyAuthExistingTokenSUCESS() {
        Integer userId = 1;
        tokenService.applyAuth(userId);
        assertThrows(RuntimeException.class, () -> tokenService.applyAuth(userId));
    }

    @Test
    @DisplayName("토큰 인증 서비스 - 성공")
    void CheckAuthExistingTokenSUCESS() {
        Integer userId = 1;
        tokenService.applyAuth(userId);
        TokenResponseDTO result = tokenService.checkAuth(userId);
        assertNotNull(result);
    }

    @Test
    @DisplayName("토큰 인증 서비스 - 실패")
    void CheckAuthNonExistingTokenSUCESS() {
        Integer userId = 1;

        assertThrows(RuntimeException.class, () -> tokenService.checkAuth(userId));
    }
}