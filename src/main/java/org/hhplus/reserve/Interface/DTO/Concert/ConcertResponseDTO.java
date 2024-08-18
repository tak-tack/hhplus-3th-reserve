package org.hhplus.reserve.Interface.DTO.Concert;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"concertName", "concertDate", "seats"}) // JSON 출력 순서를 제어
public class ConcertResponseDTO {
    private String concertName;
    private String ConcertDate;
    private List<SeatInfo> seats;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SeatInfo{
        private Integer concertSeatNum;
        private Integer concertSeatPrice;
        private ConcertSeatStatus concertSeatStatus;
    }
}
