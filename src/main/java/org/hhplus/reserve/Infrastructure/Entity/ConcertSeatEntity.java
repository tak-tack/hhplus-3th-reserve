package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.hhplus.reserve.Business.Domain.ConcertSeatDomain;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
@Builder
@Table(name = "Concert_Seat")
@NoArgsConstructor
@AllArgsConstructor
public class ConcertSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer concertSeatId;
    private Integer concertSeatNum; // 1~50
    private Integer concertSeatPrice;
    @Enumerated(EnumType.STRING)
    private ConcertSeatStatus concertSeatStatus = ConcertSeatStatus.WAITING;
    @ManyToOne
    @JoinColumn(name = "concert_option_id", nullable = false)
    private ConcertOptionEntity concertOption;
    @CreatedDate
    private String create_dt;
    @Nullable
    private String modifyDt;
    @Version
    private Integer version;

    @PrePersist // 해당 엔티티를 저장하기 이전에 실행
    public void onPrePersist() {
        this.create_dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public ConcertSeatDomain toDomain() {
        ConcertSeatDomain concertSeatDomain = new ConcertSeatDomain();
        BeanUtils.copyProperties(this, concertSeatDomain);
        return concertSeatDomain;
    }

}
