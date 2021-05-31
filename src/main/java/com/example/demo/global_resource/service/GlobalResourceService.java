package com.example.demo.global_resource.service;

import com.example.demo.global_resource.repository.GlobalResourceRepository;
import com.example.demo.global_resource.model.dto.GlobalResourceResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class GlobalResourceService {
    private final GlobalResourceRepository globalResourceRepository;

    public GlobalResourceService(GlobalResourceRepository globalResourceRepository){
        this.globalResourceRepository = globalResourceRepository;
    }



    @Transactional
    public List<GlobalResourceResponse> getResourceFileName(String resourceType){
        return globalResourceRepository.findByAllResource(resourceType);
    }

    @Transactional
    public GlobalResourceResponse getReourseWeatherFileName(String resourceType, String resourceCode){
        return globalResourceRepository.findWeatherByCode(resourceType,resourceCode);
    }

    @Transactional
    public List<GlobalResourceResponse> getReourseName(String resourceType){
        return globalResourceRepository.findByAllTemaeResourceName(resourceType);
    }



}
