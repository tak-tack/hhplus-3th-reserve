package org.hhplus.reserve.Infrastructure.DB.Concert;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Repository.ConcertRepository;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {
    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public List<Integer> findByConcertid(){
        return concertJpaRepository.findByConcertid().orElse(null);
    }
    public List<ConcertDomain> findAllConcertWithSeats(List<Integer> concertIds){
        return concertJpaRepository.findConcertsWithSeats(concertIds).stream().map(ConcertEntity::toDomain).collect(Collectors.toList());
    }

}
