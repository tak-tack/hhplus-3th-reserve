package org.hhplus.reserve.Interface.DTO.Reservation;

import lombok.*;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Interface.DTO.Concert.ConcertRequestDTO;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDTO {
    private Integer reservationId;
    private Integer userId;
    private Integer concertOptionId;
    private Integer seatId;
    private ReservationStatus reservationStatus;

         //reservationResponseDTO > ConcertRequestDTO
            public ConcertRequestDTO converting()
            {
                ConcertRequestDTO concertRequestDTO = new ConcertRequestDTO();
                BeanUtils.copyProperties(this,concertRequestDTO);
                return concertRequestDTO;
            }
}
