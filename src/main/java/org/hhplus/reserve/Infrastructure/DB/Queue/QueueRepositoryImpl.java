package org.hhplus.reserve.Infrastructure.DB.Queue;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Infrastructure.Entity.QueueEntity;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {
    private final QueueJpaRepository queueJpaRepository;
    @Override
    public List<QueueDomain> findByWaitingUuid(UUID userUuid, QueueStatus queueStatus){
        return queueJpaRepository.findByWaitingUuid(userUuid,queueStatus)
                .orElseThrow(NullPointerException::new)
                .stream().map(QueueEntity::toDomain).toList();
    }
    public List<QueueDomain> findByUuid(UUID userUuid){
        return queueJpaRepository.findByUuid(userUuid)
                .orElseThrow(NullPointerException::new)
                .stream().map(QueueEntity::toDomain).toList();
    }

    @Override
    public Integer countWaitingQueue(QueueStatus queueStatus){
        String lastCreateDt = queueJpaRepository.findLastQueue(queueStatus).orElse("9999-12-31");
        return queueJpaRepository.countWaitingQueue(lastCreateDt).orElseThrow();
    }
    @Override
    public void updateQueueStatus(String modifyDt, QueueStatus queueStatus){
            queueJpaRepository.updateQueue(modifyDt,queueStatus);

    }
    @Override
    public void saveByUserUuid(UUID userUuid,QueueStatus queueStatus){
        //queueJpaRepository.save(queueEntity);
        queueJpaRepository.saveByUserUuid(userUuid,queueStatus);
    }


}
