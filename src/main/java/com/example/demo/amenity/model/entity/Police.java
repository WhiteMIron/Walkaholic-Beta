package com.example.demo.amenity.model.entity;

import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;

@Entity
@Table(name="police")
public class Police {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name="police_name")
    private Long policeName;

    @Column(name="police_latitude")
    private String policeLatitude;

    @Column(name="police_longitude")
    private String policeLongtitude;
}
