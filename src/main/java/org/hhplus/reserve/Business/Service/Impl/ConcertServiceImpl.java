package org.hhplus.reserve.Business.Service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Domain.ConcertOptionDomain;
import org.hhplus.reserve.Business.Repository.ConcertRepository;
import org.hhplus.reserve.Business.Service.ConcertService;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
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
    public List<ConcertOptionDomain> getConcertAvailabillity(List<ConcertResponseDTO> concertList){
        //List<ConcertOptionDomain> concertList = new ArrayList<>();
        for (ConcertResponseDTO concertResponseDTO : concertList) {
            concertResponseDTO.getConcertId();
        }
        if(concertList.isEmpty())
        {
            throw new EntityNotFoundException("콘서트의 예약가능한 날짜/좌석이 없습니다.");
        }
        return null; // 콘서트 날짜/좌석 반환 Repo 작성 예정
    }


}
