package org.hhplus.reserve.Business.Service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Repository.ConcertRepository;
import org.hhplus.reserve.Business.Service.ConcertService;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {


    private final ConcertRepository concertRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public List<ConcertResponseDTO> ConcertList(){
        List<Integer> concertList = concertRepository.findByConcertid();
        if(concertList.isEmpty())
        {
            throw new EntityNotFoundException("콘서트 조회 결과가 없습니다");
            }
        log.info("Concert-Service : " + concertList.get(0));
        log.info("Concert-Service : " + concertList.get(0));
            return concertRepository.findAllConcertWithSeats(concertList)
                    .stream().flatMap(concertDomain ->
                            concertDomain.toDTO().stream()).collect(Collectors.toList());


    }


}
