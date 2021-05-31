package com.example.demo.misson.model.entity;


import com.example.demo.misson.model.entity.Mission;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Table(name="mission_condition")
@Entity
public class MissionCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="mission_condition_content")
    private String missionConditionContent;

    @Column(name="mission_condition_value")
    private Long missionConditionValue;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mission_id")
    @JsonBackReference
    private Mission missionId;
}
