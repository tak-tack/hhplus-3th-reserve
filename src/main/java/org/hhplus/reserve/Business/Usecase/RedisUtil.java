package org.hhplus.reserve.Business.Usecase;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    // redis 예시

    // key와 data를 Redis에 저장한다. 만약 데이터에 만료 시간을 설정하고 싶다면 세 번째 파라미터로 Duration 객체를 전달한다.
   public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    //getValues() : key 파라미터로 받아 key를 기반으로 데이터를 조회한다.
    @Transactional(readOnly = true)
    public String getValues(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if (values.get(key) == null) {
            return "false";
        }
        return (String) values.get(key);
    }
    //deleteValues() : key를 파라미터로 받아 key를 기반으로 데이터를 삭제한다.
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    public void expireValues(String key, int timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    //checkExistsValue() : 조회하려는 데이터가 없으면 “false”를 반환한다.
    public boolean checkExistsValue(String value) {
        return !value.equals("false");
    }



}
