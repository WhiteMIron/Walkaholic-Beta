package com.example.demo.global_resource.repository;

import com.example.demo.global_resource.model.dto.GlobalResourceResponse;
import com.example.demo.global_resource.model.entitiy.GlobalResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GlobalResourceRepository extends JpaRepository<GlobalResource,Long> {


    @Query(value = "select resource_filename as ResourceFileName from global_resource where resource_type =:resourceType",nativeQuery = true)
    List<GlobalResourceResponse> findByAllResource(String resourceType);


    @Query(value = "select resource_name as ResourceName ,resource_filename as ResourceFileName from global_resource where resource_type =:resourceType",nativeQuery = true)
    List<GlobalResourceResponse> findByAllTemaeResourceName(String resourceType);


    @Query(value =" select resource_filename as ResourceFileName from global_resource where resource_type =:resourceType and resource_code=:weatherCode",nativeQuery = true)
    GlobalResourceResponse findWeatherByCode(String resourceType,String weatherCode);


}
