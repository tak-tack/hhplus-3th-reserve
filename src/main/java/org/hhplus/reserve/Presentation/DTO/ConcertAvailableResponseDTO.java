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
        private List<AvailableSeat> availableSeat;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AvailableSeat
        {
            private List<Integer> seat;
            private LocalDateTime ConcertStartTime;
        }



}