package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Domain.TokenDomain;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@Table(name="Concert")
@AllArgsConstructor
@NoArgsConstructor
public class ConcertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer concertId;
    String concertName;

//    @OneToMany
//    @JoinColumn(name="concert_id")
//    private List<ConcertOptionEntity> concertOptionEntities = new ArrayList<>();
    Integer concertOptionId;

    public ConcertDomain toDomain()
    {
        ConcertDomain concertDomain = new ConcertDomain();
        BeanUtils.copyProperties(this,concertDomain);
        return concertDomain;

    }



}
