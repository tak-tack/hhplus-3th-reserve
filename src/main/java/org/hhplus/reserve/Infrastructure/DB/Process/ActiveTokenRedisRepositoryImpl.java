package org.hhplus.reserve.Infrastructure.DB.Process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveTokenRedisRepositoryImpl implements ActiveTokenRedisRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String ACTIVE_TOKENS_KEY  = "activeToken";

    @Override
    // 토큰 저장
    public void register(String userId){
        // 저장과 유효기간 두 기능의 원자성을 위해 파이프라인 설정
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            // Set에 userId 추가
            connection.sAdd(redisTemplate.getStringSerializer().serialize(ACTIVE_TOKENS_KEY),
                    redisTemplate.getStringSerializer().serialize(userId));

            // 키에 유효기간 설정 (예: 10분)
            connection.expire(redisTemplate.getStringSerializer().serialize(ACTIVE_TOKENS_KEY),60);

            return null; // 파이프라인에서는 반환값이 필요 없으므로 null 반환
        });
    }

    @Override
    // 활성화 토큰 만료
    public void remove(String userId){
        redisTemplate.opsForSet().remove(ACTIVE_TOKENS_KEY,userId);
    }

    @Override
    public boolean check(String userId){
        return redisTemplate.opsForSet().isMember(ACTIVE_TOKENS_KEY,userId);
    }

    @Override
    public Long count(){
        return redisTemplate.opsForSet().size(ACTIVE_TOKENS_KEY);
    }
}
