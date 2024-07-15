package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hhplus.reserve.Business.Enum.ReservationStatus;

@Setter
@Getter
@Entity
@Builder
@Table(name="RESERVATION")
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reservationId;
    private Integer userId;
    private Integer concertOptionId;
    private Integer seatId;
    private ReservationStatus reservationStatus;

}
