package org.hhplus.reserve.Business.Repository;

import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Enum.QueueStatus;


import java.util.List;
import java.util.UUID;

public interface QueueRepository {
    List<QueueDomain> findByUuid(UUID userUuid);
    List<QueueDomain> findByWaitingUuid(UUID userUuid, QueueStatus queueStatus);
    Integer countWaitingQueue(QueueStatus queueStatus);
    void updateQueueStatus(String modifyDt,QueueStatus queueStatus);
    void saveByUserUuid(UUID userUuid,QueueStatus queueStatus);
}
