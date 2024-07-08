package org.hhplus.reserve.Business.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.TokenDomain;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Presentation.DTO.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.TokenResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenResponseDTO applyAuth(Integer userId){
        return tokenRepository.save(userId).toDTO();
    }

    public TokenResponseDTO checkAuth(Integer userId){
        TokenDomain tokenDomain = new TokenDomain();
        return tokenRepository.check(userId).toDTO();
    }
}
