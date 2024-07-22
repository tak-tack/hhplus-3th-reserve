package org.hhplus.reserve.Infrastructure.DB.Queue;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Infrastructure.Entity.QueueEntity;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {
    private final QueueJpaRepository queueJpaRepository;

    @Override
    @Transactional
    public List<QueueDomain> findByWaitingUserId(Integer userId, String queueStatus){
        return queueJpaRepository.findByWaitingUserId(userId,queueStatus)
                .orElseThrow(NullPointerException::new)
                .stream().map(QueueEntity::toDomain).toList();
    }
    @Override
    @Transactional
    public List<QueueDomain> exist(Integer userId){
        return queueJpaRepository.findByUserId(userId)
                .stream().map(QueueEntity::toDomain).toList();
    }

    @Override
    @Transactional
    public Integer countWaitingQueue(QueueStatus queueStatus){
        String lastCreateDt = queueJpaRepository.findLastQueue(queueStatus).orElse("9999-12-31");
        return queueJpaRepository.countWaitingQueue(lastCreateDt).orElseThrow();
    }
    @Override
    @Transactional
    public void updateQueueStatus(String modifyDt, QueueStatus queueStatus){
            queueJpaRepository.update(modifyDt,queueStatus);

    }
    @Override
    @Transactional
    public void saveByUserId(Integer userId,String createDt,String queueStatus){
        queueJpaRepository.register(userId,createDt,queueStatus);
    }


}
