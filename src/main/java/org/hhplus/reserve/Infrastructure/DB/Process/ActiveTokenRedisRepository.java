package org.hhplus.reserve.Infrastructure.DB.Process;

public interface ActiveTokenRedisRepository {
    void register(String userId);
    void remove(String userId);
    boolean check(String userId);
    Long count();
}
