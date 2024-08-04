package org.hhplus.reserve.Business.Repository;

public interface ActiveTokenRedisRepository {
    void register(String userId);
    void remove(String userId);
    boolean check(String userId);
    Long count();
}
