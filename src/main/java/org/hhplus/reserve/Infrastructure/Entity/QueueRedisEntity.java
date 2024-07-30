package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data // Getter, Setter, toString, equals, hashcode
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드 값 받는 생성자
@Builder // 빌더 패턴 클래스 자동생성
@RedisHash("QueueRedis")
public class QueueRedisEntity implements Serializable { // java에서 직렬화할 수 있도록 하는 마커
    // 인터페이스직렬화(Serialization)는 객체의 상태를 바이트 스트림으로 변환하여 저장하거나 전송할 수 있게 하는 과정입니다.
    // 역직렬화(Deserialization)는 이 바이트 스트림을 다시 객체로 복원하는 과정입니다.
    @Id
    private String queueId;
    private String userId;
    private LocalDateTime validFromTime;
    private LocalDateTime validToTime;

    @Builder
    public QueueRedisEntity(String queueId, String userId){
        this.queueId = queueId;
        this.userId = userId;
        this.validFromTime = LocalDateTime.now();
        this.validToTime = this.validFromTime.plusHours(1); // +1H
    }


}
