package org.hhplus.reserve.Business.Repository;

public interface TokenRedisRepository {
    void register(String userId ,String token ,Long expirationTimeMillis);
    String get(String userId);
    void delete(String userId);
    boolean check(String userId);
}
