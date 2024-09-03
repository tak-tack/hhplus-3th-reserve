package org.hhplus.reserve.Infrastructure.DB.Token;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.User.model.TokenDomain;
import org.hhplus.reserve.Infrastructure.Entity.TokenEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {
    private final TokenJpaRepository tokenJpaRepository;

    // 토큰 발급
    @Override
    public TokenDomain save(UUID userUuid){
        TokenEntity tokenEntity =tokenJpaRepository.save(
                TokenEntity.builder().user_UUID(userUuid).build());
        return tokenEntity.toDomain();
    }

    // 토큰 중복 확인
    @Override
    public Optional<UUID> exist(String userUuid){
        return tokenJpaRepository.existsByUserUUID(userUuid);
    }

    // 토큰 요청
    @Override
    public TokenDomain select(UUID userUuid){
        return tokenJpaRepository.findByUserId(userUuid).toDomain();
    }

    //토큰만료
    @Override
    public void delete(UUID userUuid){
        tokenJpaRepository.deleteByUserId(userUuid);
    }

}
