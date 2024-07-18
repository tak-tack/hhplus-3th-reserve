package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="Queue")
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer queueId;
    private UUID userUUID;
    private QueueStatus queueStatus;
    @CreatedDate
    private String create_dt;
    @Nullable
    private String modify_dt;

    @PrePersist // 해당 엔티티를 저장하기 이전에 실행
    public void onPrePersist(){
        this.create_dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public QueueDomain toDomain()
    {
        QueueDomain queueDomain = new QueueDomain();
        BeanUtils.copyProperties(this,queueDomain);
        return queueDomain;

    }

}
