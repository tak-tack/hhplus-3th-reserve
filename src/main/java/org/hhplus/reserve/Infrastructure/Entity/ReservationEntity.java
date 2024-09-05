package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import org.hhplus.reserve.Business.Domain.Reservation.model.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

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
    //private Integer userId;
    private UUID userUuid;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
    private String createDt;
    @Nullable
    private String modifyDt;

    public ReservationDomain toDomain()
    {
        ReservationDomain reservationDomain = new ReservationDomain();
        BeanUtils.copyProperties(this,reservationDomain);
        return reservationDomain;

    }

}
