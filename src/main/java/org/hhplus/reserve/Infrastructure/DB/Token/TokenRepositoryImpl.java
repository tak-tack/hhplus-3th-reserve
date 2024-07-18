package org.hhplus.reserve.Infrastructure.DB.Token;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.TokenDomain;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Infrastructure.Entity.TokenEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {
    private final TokenJpaRepository tokenJpaRepository;

    // 토큰 발급
    public TokenDomain save(Integer userId){
        TokenEntity tokenEntity =tokenJpaRepository.save(
                TokenEntity.builder().userId(userId).build());
        return tokenEntity.toDomain();
    }

    // 토큰 중복 확인
    public boolean exist(Integer userId){
        return tokenJpaRepository.existsByUserId(userId);
    }

    // 토큰 요청
    public TokenDomain select(Integer userId){
        return tokenJpaRepository.findByUserId(userId).toDomain();
    }

}
