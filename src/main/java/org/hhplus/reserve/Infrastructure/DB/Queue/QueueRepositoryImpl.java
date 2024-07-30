package org.hhplus.reserve.Infrastructure.DB.Queue;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Infrastructure.Entity.QueueEntity;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {
    private static final Logger log = LoggerFactory.getLogger(QueueRepositoryImpl.class);
    private final QueueJpaRepository queueJpaRepository;


    @Override
    public List<QueueDomain> findByUserId(Integer userId, QueueStatus queueStatus){
        return queueJpaRepository.findByWaitingUserId(userId,queueStatus)
                .orElseThrow(NullPointerException::new)
                .stream().map(QueueEntity::toDomain).toList();
    }
    @Override
    public List<QueueDomain> exist(Integer userId){
        return queueJpaRepository.findByUserId(userId)
                .stream().map(QueueEntity::toDomain).toList();
    }

    @Override
    public Integer countWaitingQueue(QueueStatus queueStatus){
        String lastCreateDt = queueJpaRepository.findLastQueue(queueStatus).orElse("9999-12-31");
        return queueJpaRepository.countWaitingQueue(lastCreateDt).orElseThrow();
    }
    @Override
    public List<Integer> findQueueIdsByStatus(QueueStatus queueStatus){
            return queueJpaRepository.findQueueIdsByStatus(queueStatus);

    }

    @Override
    public void updateQueueStatusByIds(String modifyDt, QueueStatus queueStatus,List<Integer> queueIds)
    {
        queueJpaRepository.updateQueueStatusByIds(modifyDt, queueStatus, queueIds);

    }

    @Override
    @Transactional
    // 저장
    public void register(QueueDomain queueDomain){
        queueJpaRepository.save(new QueueEntity(queueDomain));
    }





}
