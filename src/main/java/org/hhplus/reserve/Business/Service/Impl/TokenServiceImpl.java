package org.hhplus.reserve.Business.Service.Impl;

import org.hhplus.reserve.Business.Domain.TokenDomain;
import org.hhplus.reserve.Presentation.DTO.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.TokenResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl {
    public TokenResponseDTO checkAuth(TokenRequestDTO tokenRequestDTO){
        TokenDomain tokenDomain = new TokenDomain();
        return tokenDomain.toDTO();
    }
}
