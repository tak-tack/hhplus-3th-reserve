package org.hhplus.reserve.Presentation.DTO;
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
        private List<AvailableSeatDTO> availableSeats;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AvailableSeatDTO
        {
            private List<Integer> seats;
            private LocalDateTime ConcertStartTime;
        }



}