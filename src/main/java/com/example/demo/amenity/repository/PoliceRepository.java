package com.example.demo.amenity.repository;

import com.example.demo.amenity.model.entity.Police;
import com.example.demo.amenity.model.dto.PoliceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



//DB에 포인트형 저장하는법
//public void saveUser() {
//        String name = "momentjin"
//        Double latitude = 32.123;
//        Double longitude = 127.123;
//        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
//
//        // WKTReader를 통해 WKT를 실제 타입으로 변환합니다.
//        Point point = (Point) new WKTReader().read(pointWKT);
//        User user = new User(name, point);
//        userRepository.save(driverLocation);
//        }

public interface PoliceRepository extends JpaRepository<Police,Long> {
;

    @Query(value="SELECT police_name as Name, police_address as Address, police_latitude as Latitude, police_longitude as Longitude, ST_DISTNACE_SPHERE( POINT(:x ,:y), police_point) *0.001 AS dist \n" +
            "FROM police HAVING dist <2\n" +
            "ORDER BY dist;",nativeQuery = true)
    List<PoliceResponse> findAllPoint(String x, String y);


}
