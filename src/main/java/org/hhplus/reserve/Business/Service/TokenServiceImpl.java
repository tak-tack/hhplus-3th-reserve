package org.hhplus.reserve.Business.Service;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Business.Service.TokenService;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Autowired
    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public TokenResponseDTO applyAuth(Integer userId){
        boolean existToken =  tokenRepository.exist(userId);
        if(!existToken)
        {
            return tokenRepository.save(userId).toDTO();
        }else{ // 중복 예외처리
            throw new CustomException(ErrorCode.USER_DUPLICATED,userId.toString());
        }

    }

    @Override
    @Transactional
    public TokenResponseDTO checkAuth(Integer userId){
        boolean existToken =  tokenRepository.exist(userId);
        if(existToken)
        {
            return tokenRepository.select(userId).toDTO();
        }else{ // user 토큰 확인 불가
            throw new CustomException(ErrorCode.USER_NOT_FOUND,userId.toString());
        }

    }
}
