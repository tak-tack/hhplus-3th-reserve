package org.hhplus.reserve.Business.Repository;

import org.hhplus.reserve.Business.Domain.QueueDomain;

import java.util.Set;

public interface QueueRedisRepository {
    void register(String userId,Long score);
    Set<String> getRange(Long min, Long max);
    Long removeRange(Long min, Long max);
    Double getScore(String userId);
    void remove(String userId);
    Long getRank(String userId);
    Long count();
}
