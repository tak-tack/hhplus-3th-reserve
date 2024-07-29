package org.hhplus.reserve.Business.Usecase;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public void setData(String key, String value, Long expiredTime){
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MICROSECONDS); // String
    }

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key); //String
    }

    public void deleteData(String key){
        redisTemplate.delete(key);
    }

}
