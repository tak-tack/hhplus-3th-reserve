package org.hhplus.reserve.Infrastructure.DB.Queue;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Repository.QueueRedisRepository;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class QueueRedisRepositoryImpl implements QueueRedisRepository {
    private static final Logger log = LoggerFactory.getLogger(QueueRedisRepositoryImpl.class);
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void register(QueueDomain queueDomain){
        Long score = Instant.now().toEpochMilli();
        log.info("redis score: "+ score);
        log.info("redis UserId: "+ queueDomain.getUserId().toString());
        redisTemplate.opsForZSet().add("Queue",queueDomain.getUserId().toString(),score);
    }

    @Override
    public Set<String> getQueue(Long start, Long end){
        return redisTemplate.opsForZSet().range("Queue",start,end);
    }


}
