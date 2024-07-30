package org.hhplus.reserve.Business.Repository;

import org.hhplus.reserve.Business.Domain.QueueDomain;

import java.util.Set;

public interface QueueRedisRepository {
    void register(QueueDomain queueDomain);
    Set<String> getQueue(Long start, Long end);
}
