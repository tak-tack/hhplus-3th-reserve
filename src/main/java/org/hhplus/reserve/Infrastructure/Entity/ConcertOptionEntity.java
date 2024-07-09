package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Concert_Option")
@AllArgsConstructor
@NoArgsConstructor
public class ConcertOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@OneToMany Consert_Seat
    @Column(name="concert_option_id")
    private Integer concertOptionId;
    @ManyToOne // ConcertEntity
    @JoinColumn(name="concert_id")
//    //@Column(name="concert_id")
//    private ConcertEntity concertEntity;
    @Column(name="concert_date")
    private LocalDateTime concertDate;
}
