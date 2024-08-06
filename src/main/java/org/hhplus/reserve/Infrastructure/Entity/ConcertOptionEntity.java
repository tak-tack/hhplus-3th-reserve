package org.hhplus.reserve.Infrastructure.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.hhplus.reserve.Business.Domain.ConcertOptionDomain;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@Table(name="Concert_Option")
@AllArgsConstructor
@NoArgsConstructor
public class ConcertOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="concert_option_id")
    private Integer concertOptionId;
    @ManyToOne
    @JoinColumn(name = "concert_id", nullable = false)
    @JsonBackReference // 순환 참조 방지를 위해 직렬화에서 제외
    private ConcertEntity concert; // concert 테이블과 join 관계
    @OneToMany(mappedBy = "concertOption", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ConcertSeatEntity> concertSeats; // concertseat 테이블과 join 관계
    @Column(name="concert_date")
    private String ConcertDate;
    @CreatedDate
    private String create_dt;

    @PrePersist // 해당 엔티티를 저장하기 이전에 실행
    public void onPrePersist(){
        this.create_dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
    }

    public ConcertOptionDomain toDomain(){
        ConcertOptionDomain concertOptionDomain = new ConcertOptionDomain();
        BeanUtils.copyProperties(this, concertOptionDomain);
        concertOptionDomain.setConcertSeats(this.concertSeats.stream()
                .map(ConcertSeatEntity::toDomain)
                .collect(Collectors.toSet()));
        return concertOptionDomain;
    }

}
