package org.hhplus.reserve.Business.Service;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
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
    // 토큰 발급
    public TokenResponseDTO applyAuth(Integer userId){
//        tokenRepository.exist(userId).orElseThrow(()
//                -> new CustomException(ErrorCode.USER_DUPLICATED,userId.toString())); // 중복체크
        if(tokenRepository.exist(userId).isPresent()){ // 중복 체크
            throw new CustomException(ErrorCode.USER_DUPLICATED,userId.toString());
        }else{
            return tokenRepository.save(userId).toDTO();

        }
    }

    @Override
    @Transactional
    // 토큰 체크
    public TokenResponseDTO checkAuth(Integer userId){
        tokenRepository.exist(userId).orElseThrow(()
                -> new CustomException(ErrorCode.USER_NOT_FOUND,userId.toString())); // 중복체크
        return tokenRepository.select(userId).toDTO();
//        if(existToken) // 토큰 존재 확인
//        {
//              return tokenRepository.select(userId).toDTO();
//        }else{ // user 토큰 확인 불가
//            throw new CustomException(ErrorCode.USER_NOT_FOUND,userId.toString());
//        }

    }

    @Override
    @Transactional
    // 토큰 만료
    public void expireToken(Integer userId){
        tokenRepository.delete(userId);
    }
}
