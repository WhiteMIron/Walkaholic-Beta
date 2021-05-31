package com.example.demo.misson.model.entity;


import javax.persistence.*;

@Table( name= "mission")
@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="mission_name")
    private String missionName;



}
