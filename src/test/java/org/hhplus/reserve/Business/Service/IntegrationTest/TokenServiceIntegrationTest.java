package org.hhplus.reserve.Business.Service.IntegrationTest;

import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Business.Service.TokenServiceImpl;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
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
//    @Autowired
//    private TokenRepository tokenRepository;

//    @Test
//    @DisplayName("토큰 발급 테스트 - 성공")
//    void ApplyAuthNewTokenSUCESS() {
//        Integer userId = 1;
//        boolean existBefore = tokenRepository.exist(userId);
//        assertFalse(existBefore);
//        TokenResponseDTO result = tokenService.applyAuth(userId);
//        assertNotNull(result);
//        assertTrue(tokenRepository.exist(userId));
//    }

    @Test
    void ApplyAuthExistingToken() {
        Integer userId = 1;
        tokenService.applyAuth(userId);
        assertThrows(RuntimeException.class, () -> tokenService.applyAuth(userId));
    }

    @Test
    void testCheckAuthExistingToken() {
        Integer userId = 1;
        tokenService.applyAuth(userId);
        TokenResponseDTO result = tokenService.checkAuth(userId);
        assertNotNull(result);
    }

    @Test
    void testCheckAuthNonExistingToken() {
        Integer userId = 1;

        assertThrows(RuntimeException.class, () -> tokenService.checkAuth(userId));
    }
}