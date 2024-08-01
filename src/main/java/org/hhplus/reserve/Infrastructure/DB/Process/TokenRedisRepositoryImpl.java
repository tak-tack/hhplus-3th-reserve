package org.hhplus.reserve.Infrastructure.DB.Process;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Repository.TokenRedisRepository;
import org.hhplus.reserve.Business.Repository.TokenRedisRepository;
import org.hhplus.reserve.Infrastructure.DB.Queue.QueueRedisRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenRedisRepositoryImpl implements TokenRedisRepository {
    private static final Logger log = LoggerFactory.getLogger(QueueRedisRepositoryImpl.class);
    private final RedisTemplate<String, String> redisTemplate;
    private static final String USER_SESSION_KEY_PREFIX = "userSession : ";
    @Override
    //토큰 저장
    public void register(String userId ,String token ,Long expirationTimeMillis){
        log.info("redis token save UserId: "+ userId);
        log.info("redis token save token: "+ token);
        log.info("redis token save expirationTimeMillis: "+ expirationTimeMillis);
        redisTemplate.opsForValue().set(USER_SESSION_KEY_PREFIX+userId,token,expirationTimeMillis, TimeUnit.MILLISECONDS);
        log.info("#token# |Test -> Controller -> Facade -> Service -> Repository(Redis)| SAVE SUCCESS");
    }

    @Override
    // 토큰 조회
    public String get(String userId){
        log.info("redis token get UserId : "+ userId);
        return redisTemplate.opsForValue().get(USER_SESSION_KEY_PREFIX+userId);
    }

    @Override
    public void delete(String userId){
        log.info("redis token delete UserId : "+ userId);
        redisTemplate.delete(USER_SESSION_KEY_PREFIX+userId);
    }

    @Override
    // 처리열 확인
    public boolean check(String userId){
        return redisTemplate.hasKey(USER_SESSION_KEY_PREFIX+userId);
    }
}

