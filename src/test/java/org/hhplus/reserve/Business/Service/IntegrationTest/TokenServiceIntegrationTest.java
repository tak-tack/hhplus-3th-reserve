package org.hhplus.reserve.Business.Service.IntegrationTest;

import jakarta.transaction.Transactional;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Business.Service.TokenServiceImpl;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class TokenServiceIntegrationTest {
    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
        // 초기화 작업
    }

    @Test
    void testApplyAuthNewToken() {
        Integer userId = 1;

        boolean existBefore = tokenRepository.exist(userId);
        assertFalse(existBefore);

        TokenResponseDTO result = tokenService.applyAuth(userId);

        assertNotNull(result);
        assertTrue(tokenRepository.exist(userId));
    }

    @Test
    void testApplyAuthExistingToken() {
        Integer userId = 1;

        tokenService.applyAuth(userId);

        assertThrows(RuntimeException.class, () -> {
            tokenService.applyAuth(userId);
        });
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

        assertThrows(RuntimeException.class, () -> {
            tokenService.checkAuth(userId);
        });
    }
}