package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.hhplus.reserve.Business.Domain.Concert.model.ConcertDomain;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ConcertOptionEntity> concertOptions; // concert_option 테이블과 join 관계

    public ConcertDomain toDomain()
    {
        ConcertDomain concertDomain = new ConcertDomain();
        BeanUtils.copyProperties(this,concertDomain);
        concertDomain.setConcertOptions(this.concertOptions.stream()
                .map(ConcertOptionEntity::toDomain)
                .collect(Collectors.toSet()));
        return  concertDomain;

    }



}
