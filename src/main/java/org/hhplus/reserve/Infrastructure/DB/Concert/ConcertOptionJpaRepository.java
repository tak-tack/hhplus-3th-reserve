package org.hhplus.reserve.Infrastructure.DB.Concert;

import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.hhplus.reserve.Infrastructure.Entity.ConcertOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConcertOptionJpaRepository  extends JpaRepository<ConcertOptionEntity, Integer> {
    //@Query("")
}
