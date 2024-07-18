package org.hhplus.reserve.Infrastructure.DB.Concert;

import org.hhplus.reserve.Infrastructure.Entity.ConcertSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertSeatJpaRepository extends JpaRepository <ConcertSeatEntity,Integer> {
}
