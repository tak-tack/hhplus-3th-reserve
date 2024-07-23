package org.hhplus.reserve.Business.Repository;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Enum.QueueStatus;

import java.util.List;

public interface QueueRepository {
    List<QueueDomain> exist(Integer userId);
    List<QueueDomain>  findByUserId(Integer userId, QueueStatus queueStatus);
    Integer countWaitingQueue(QueueStatus queueStatus);
    List<Integer> findQueueIdsByStatus(QueueStatus queueStatus);
    void saveByUserId(Integer userId,String createDt,String queueStatus);
    void updateQueueStatusByIds(String modifyDt, QueueStatus queueStatus,List<Integer> queueIds);
}
