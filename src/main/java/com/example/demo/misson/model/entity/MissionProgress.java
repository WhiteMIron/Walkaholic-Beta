package com.example.demo.misson.model.entity;

import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name="mission_progress")
public class MissionProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="complete_yn")
    private Long completeYn;

    @Column(name="current_achievement")
    private Long currentAchievement;

    @Column(name="take_reward_yn")
    private Long takeRewardYN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="mission_id")
    private Mission missionId;



}

