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
    private Integer concertOptionId;
    @ManyToOne // ConcertEntity
    @JoinColumn
    private ConcertEntity concertId;
    private LocalDateTime concertDate;
}
