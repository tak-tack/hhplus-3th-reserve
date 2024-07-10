package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
@Table(name="Concert_Seat")
@NoArgsConstructor
@AllArgsConstructor
public class ConcertSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer seatId;
    private Integer concertOptionId;
    private Integer seatNum; // 1~50
    private Integer seatPrice;
    // seatStatus
    @CreatedDate
    private String create_dt;

    @PrePersist // 해당 엔티티를 저장하기 이전에 실행
    public void onPrePersist(){
        this.create_dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}
