package org.hhplus.reserve.Business.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.TokenDomain;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Presentation.DTO.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.TokenResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);
    private final TokenRepository tokenRepository;

    public TokenResponseDTO applyAuth(Integer userId){
        boolean existToken =  tokenRepository.exist(userId);
        if(!existToken)
        {
            log.info("token-service 토큰 발급 성공 : " + userId);
            return tokenRepository.save(userId).toDTO();
        }else{
            throw new RuntimeException();
        }

    }

    public TokenResponseDTO checkAuth(Integer userId){
        boolean existToken =  tokenRepository.exist(userId);
        if(existToken)
        {
            log.info("token-service 토큰 조회 성공" + userId);
            return tokenRepository.select(userId).toDTO();
        }else{
            throw new RuntimeException();
        }

    }
}
