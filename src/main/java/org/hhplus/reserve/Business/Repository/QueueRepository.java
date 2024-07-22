package org.hhplus.reserve.Business.Repository;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Enum.QueueStatus;

import java.util.List;

public interface QueueRepository {
    List<QueueDomain> exist(Integer userId);
    List<QueueDomain>  findByWaitingUserId(Integer userId, String queueStatus);
    Integer countWaitingQueue(QueueStatus queueStatus);
    void updateQueueStatus(String modifyDt,QueueStatus queueStatus);
    void saveByUserId(Integer userId,String createDt,String queueStatus);
}
