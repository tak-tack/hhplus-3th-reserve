package org.hhplus.reserve.Infrastructure.DB.Process;

import org.hhplus.reserve.Business.Repository.ActiveTokenRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveTokenRedisRepositoryImpl implements ActiveTokenRedisRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String ACTIVE_TOKENS_KEY  = "activeToken";

    @Override
    public void register(String userId){
        redisTemplate.opsForSet().add(ACTIVE_TOKENS_KEY,userId);
    }

    @Override
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
