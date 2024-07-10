package org.hhplus.reserve.Business.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Business.Service.TokenService;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenResponseDTO applyAuth(Integer userId){
        boolean existToken =  tokenRepository.exist(userId);
        if(!existToken)
        {
            return tokenRepository.save(userId).toDTO();
        }else{ // 중복 예외처리
            throw new RuntimeException();
        }

    }

    public TokenResponseDTO checkAuth(Integer userId){
        boolean existToken =  tokenRepository.exist(userId);
        if(existToken)
        {
            return tokenRepository.select(userId).toDTO();
        }else{ // user 토큰 확인 불가
            throw new RuntimeException();
        }

    }
}
