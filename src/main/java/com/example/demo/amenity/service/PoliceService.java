package com.example.demo.amenity.service;

import com.example.demo.amenity.model.dto.PoliceResponse;
import com.example.demo.amenity.repository.PoliceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PoliceService
{

    private final PoliceRepository pointRepository;


    public PoliceService(PoliceRepository pointRepository){

        this.pointRepository = pointRepository;
    }


    @Transactional
    public List<PoliceResponse> getPoint(String x, String y){

        return pointRepository.findAllPoint(x,y);
    }

}
