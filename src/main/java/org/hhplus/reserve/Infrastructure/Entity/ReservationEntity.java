package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Domain.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@Entity
@Builder
@Table(name="RESERVATION")
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    private Integer concertOptionId;
    private Integer seatId;
    private Integer userId;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
    @CreatedDate
    private String createDt;
    @Nullable
    private String modifyDt;

    @PrePersist // 해당 엔티티를 저장하기 이전에 실행
    public void onPrePersist(){
        this.createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
    public ReservationDomain toDomain()
    {
        ReservationDomain reservationDomain = new ReservationDomain();
        BeanUtils.copyProperties(this,reservationDomain);
        return reservationDomain;

    }

}
