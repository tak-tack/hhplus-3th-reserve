package org.hhplus.reserve.Interface.DTO.ConcertAvailable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertAvailableResponseDTO {


    //public static class Available

        private Integer concertOptionId;
        //private LocalDateTime ConcertStartTime;

        @Getter
        @Setter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AvailableSeatDTO
        {
            private LocalDateTime availableSeats;
            private List<Integer> seats;

        }



}