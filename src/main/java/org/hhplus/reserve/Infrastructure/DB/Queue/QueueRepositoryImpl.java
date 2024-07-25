package org.hhplus.reserve.Infrastructure.DB.Queue;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Infrastructure.Entity.QueueEntity;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        log.info("updateQueueStatusByIds : " +queueStatus);
        queueJpaRepository.updateQueueStatusByIds(modifyDt, queueStatus, queueIds);
        //
    }

    @Override
    @Transactional
    public void saveByUserId(Integer userId,String createDt,String queueStatus){
        queueJpaRepository.register(userId,createDt,queueStatus);
    }


}
