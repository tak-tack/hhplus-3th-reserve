package org.hhplus.reserve.Infrastructure.DB.Queue;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Repository.QueueRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class QueueRedisRepositoryImpl implements QueueRedisRepository {
    private static final Logger log = LoggerFactory.getLogger(QueueRedisRepositoryImpl.class);
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    // 대기열 저장
    public void register(String userId,Long score){
        log.info("Queue redis save UserId: "+ userId);
        redisTemplate.opsForZSet().add("Queue",userId,score);
        //log.info("#Queue# |Test -> Controller -> Facade -> Service -> Repository(Redis)| SAVE SUCCESS");
    }

    @Override
    // min ~ max 범위 값 조회
    public Set<String> getRange(Long min, Long max){
        return redisTemplate.opsForZSet().range("Queue",min,max); // 0 ~ 50
    }

    @Override
    // min ~ max 범위 값 삭제. 삭제 갯수 반환
    public Long removeRange(Long min, Long max){
        return redisTemplate.opsForZSet().removeRange("Queue",min,max);
    }


    @Override
    // score 조회
    public Double getScore(String userId){
        return  redisTemplate.opsForZSet().score("Queue",userId);
        // rank : 원하는걸 바로줌. >>> 구글링. 순위보장필요없음. 트래픽들어온 순번도 보장해야함.
        // 보장한다면 밀리세컨드. 들어오는게 중요하지 정확히 몇번째라는게 중요한거 .. socre가 겹치는애들이 있으면 redis 에서 판단할때 알아서 한다.
        // 순번의위치는 고정되므로 중복되도 나머지꺼에 영향x 경계값으로 될경우 안타깝지만 나가리
    }

    @Override
    // rank 조회
    public Long getRank(String userId){
        return redisTemplate.opsForZSet().rank("Queue",userId);
    }

    @Override
    // 삭제
    public void remove(String userId){
        redisTemplate.opsForZSet().remove("Queue",userId);
    }

    @Override
    // 등록 수 조회
    public Long count(){
        return redisTemplate.opsForZSet().size("Queue");
    }


}
