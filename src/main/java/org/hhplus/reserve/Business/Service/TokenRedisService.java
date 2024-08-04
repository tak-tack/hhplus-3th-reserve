package org.hhplus.reserve.Business.Service;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Infrastructure.DB.Process.ActiveTokenRedisRepository;
import org.hhplus.reserve.Infrastructure.DB.Token.TokenRepository;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenRedisService {

    @Autowired
    private final TokenRepository tokenRepository;
    @Autowired
    private final ActiveTokenRedisRepository activeTokenRedisRepository;

    @Transactional
    // 토큰 발급
    public void saveToken(String userId) {
        if (tokenRepository.exist(Integer.parseInt(userId)).isPresent()) { // 중복 체크
            throw new CustomException(ErrorCode.USER_DUPLICATED, userId);
        } else {
            tokenRepository.save(Integer.parseInt(userId));
        }
    }

    // 토큰 발급 검증
    public boolean checkToken(String userId) {
        return tokenRepository.exist(Integer.parseInt(userId)).isPresent();
    }

    // 토큰 활성화
    @Transactional
    public void activeToken(String userId) {
        if (tokenRepository.exist(Integer.parseInt(userId)).isPresent()) { // 데이터 유무 확인
            activeTokenRedisRepository.register(userId);
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    //토큰 활성화 수
    @Transactional
    public Long activeTokenCount() {
        return activeTokenRedisRepository.count();
    }

    // 토큰 활성화 상태 확인
    @Transactional
    public boolean validateActiveToken(String userId) {
        return activeTokenRedisRepository.check(userId);
    }

    //토큰 비활성화
    @Transactional
    public void deactivateToken(String useId) {
        activeTokenRedisRepository.remove(useId);
    }

}
