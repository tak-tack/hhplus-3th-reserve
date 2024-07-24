//package org.hhplus.reserve.Business.Service.UnitTest;
//
//import org.hhplus.reserve.Business.Domain.TokenDomain;
//import org.hhplus.reserve.Business.Repository.TokenRepository;
//import org.hhplus.reserve.Business.Service.TokenServiceImpl;
//import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TokenServiceUnitTest {
//
//    @Mock
//    private TokenRepository tokenRepository;
//
//    @InjectMocks
//    private TokenServiceImpl tokenService;
//
//    @BeforeEach
//    void setUp() {
//        // 초기화 작업
//    }
//
//    @Test
//    void testApplyAuthNewToken() {
//        Integer userId = 1;
//        TokenDomain tokenDomain = new TokenDomain();
//
//        when(tokenRepository.exist(userId)).thenReturn(false);
//        when(tokenRepository.save(userId)).thenReturn(tokenDomain);
//        when(tokenDomain.toDTO()).thenReturn(new TokenResponseDTO());
//
//        TokenResponseDTO result = tokenService.applyAuth(userId);
//
//        assertNotNull(result);
//        verify(tokenRepository).exist(userId);
//        verify(tokenRepository).save(userId);
//    }
//
//    @Test
//    void testApplyAuthExistingToken() {
//        Integer userId = 1;
//
//        when(tokenRepository.exist(userId)).thenReturn(true);
//
//        assertThrows(RuntimeException.class, () -> {
//            tokenService.applyAuth(userId);
//        });
//
//        verify(tokenRepository).exist(userId);
//        verify(tokenRepository, never()).save(userId);
//    }
//
//    @Test
//    void testCheckAuthExistingToken() {
//        Integer userId = 1;
//        TokenDomain tokenDomain = new TokenDomain();
//
//        when(tokenRepository.exist(userId)).thenReturn(true);
//        when(tokenRepository.select(userId)).thenReturn(tokenDomain);
//        when(tokenDomain.toDTO()).thenReturn(new TokenResponseDTO());
//
//        TokenResponseDTO result = tokenService.checkAuth(userId);
//
//        assertNotNull(result);
//        verify(tokenRepository).exist(userId);
//        verify(tokenRepository).select(userId);
//    }
//
//    @Test
//    void testCheckAuthNonExistingToken() {
//        Integer userId = 1;
//
//        when(tokenRepository.exist(userId)).thenReturn(false);
//
//        assertThrows(RuntimeException.class, () -> {
//            tokenService.checkAuth(userId);
//        });
//
//        verify(tokenRepository).exist(userId);
//        verify(tokenRepository, never()).select(userId);
//    }
//}