package org.hhplus.reserve.Infrastructure.DB.Token;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.User.model.TokenDomain;
import org.hhplus.reserve.Infrastructure.Entity.TokenEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {
    private final TokenJpaRepository tokenJpaRepository;

    // 토큰 발급
    @Override
    public TokenDomain save(Integer userId){
        TokenEntity tokenEntity =tokenJpaRepository.save(
                TokenEntity.builder().userId(userId).build());
        return tokenEntity.toDomain();
    }

    // 토큰 중복 확인
    @Override
    public Optional<Integer> exist(Integer userId){
        return tokenJpaRepository.existsByUserId(userId);
    }

    // 토큰 요청
    @Override
    public TokenDomain select(Integer userId){
        return tokenJpaRepository.findByUserId(userId).toDomain();
    }

    //토큰만료
    @Override
    public void delete(Integer userId){
        tokenJpaRepository.deleteByUserId(userId);
    }

}
