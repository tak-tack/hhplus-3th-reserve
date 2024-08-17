package org.hhplus.reserve.Business.Service;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Infrastructure.DB.Queue.QueueRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class QueueRedisService {
    private static final Logger log = LoggerFactory.getLogger(QueueRedisService.class);
    private final QueueRedisRepository queueRedisRepository;

    long queueSize = 10;
    long min = 0;
    long max = min + queueSize - 1;


    // 대기열 등록
    public void saveQueue(Integer userId){
        Long score = Instant.now().toEpochMilli();
        queueRedisRepository.register(userId.toString(),score);
    }

    // 대기열
    public void passQueue(){
//        Set<String> userIds = queueRedisRepository.getRange(min,max);
//        for(String userId : userIds) {
//        log.info("pass userId : "+userId);
//        }
        Long removeCount = queueRedisRepository.removeRange(min,max);
        //log.info("==================== removeCount ====================  : "+ removeCount);
    }

    // 상위 50 대기열 정보 호출
    public Set<String> getQueueTop50(){
        return queueRedisRepository.getRange(0L,49L);
    }

    // 대기열 score 확인
    public Double getQueueScore(String userId){
        return queueRedisRepository.getScore(userId);
    }

    // 대기열 삭제
    public void removeQueue(String userId){
        queueRedisRepository.remove(userId);
    }

    // 대기열 랭크
    public Long getQueueRank(String userId){
        return queueRedisRepository.getRank(userId);
    }
}
