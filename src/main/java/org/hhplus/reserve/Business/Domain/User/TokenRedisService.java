package org.hhplus.reserve.Business.Domain.User;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Infrastructure.DB.Process.ActiveTokenRedisRepository;
import org.hhplus.reserve.Infrastructure.DB.Token.TokenRepository;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenRedisService {

    @Autowired
    private final TokenRepository tokenRepository;
    @Autowired
    private final ActiveTokenRedisRepository activeTokenRedisRepository;

    @Transactional
    // 토큰 발급
    public void saveToken(String userUuid) {
        if (tokenRepository.exist(userUuid).isPresent()) { // 중복 체크
            throw new CustomException(ErrorCode.USER_DUPLICATED, userUuid);
        } else {
            tokenRepository.save(UUID.fromString(userUuid));
        }
    }

    // 토큰 발급 검증
    public boolean checkToken(String userUuid) {
              return tokenRepository.exist(userUuid).isPresent();

    }

    // 토큰 활성화
    @Transactional
    public void activeToken(String userUuid) {
            activeTokenRedisRepository.register(userUuid);

    }

    //토큰 활성화 수
    @Transactional
    public Long activeTokenCount() {
        return activeTokenRedisRepository.count();
    }

    // 토큰 활성화 상태 확인
    @Transactional
    public boolean validateActiveToken(String userUuid) {
        return activeTokenRedisRepository.check(userUuid);
    }

    //토큰 비활성화
    public void deactivateToken(String userUuid) {
        activeTokenRedisRepository.remove(userUuid);
    }

}
