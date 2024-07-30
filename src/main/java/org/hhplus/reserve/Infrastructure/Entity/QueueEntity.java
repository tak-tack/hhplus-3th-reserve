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


@Getter
@Setter
@Entity
@Table(name="Queue")
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntity implements CommonEntity<QueueDomain>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer queueId;
    private Integer userId;
    @Enumerated(EnumType.STRING)
    private QueueStatus queueStatus;
    private String createDt;
    @Nullable
    private String modifyDt;

    // domain > entity converting
    public QueueEntity(QueueDomain queueDomain){
        BeanUtils.copyProperties(queueDomain,this);
    }

    // entity > domain converting
    @Override
    public QueueDomain toDomain()
    {
        QueueDomain queueDomain = new QueueDomain();
        BeanUtils.copyProperties(this,queueDomain);
        return queueDomain;

    }

}
