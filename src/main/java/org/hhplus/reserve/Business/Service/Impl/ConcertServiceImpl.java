package org.hhplus.reserve.Business.Service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Repository.ConcertRepository;
import org.hhplus.reserve.Business.Service.ConcertService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;

    @Override
    public List<ConcertDomain> ConcertList(){
        List<ConcertDomain> concertList = concertRepository.findAll();
        if(concertList.isEmpty())
        {
            throw new EntityNotFoundException("콘서트 결과가 없습니다");
            }
            return concertList;
    }


}
