package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
