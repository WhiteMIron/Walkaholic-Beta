package com.example.demo.walkrecord.model.entity;

import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Table ( name= "walk_record")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class WalkRecord {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="walk_date")
    private Date walkDate;
    @Column(name="walk_distance")
    private Float walkDistance;
    @Column(name="walk_time")
    private Time walkTime;
    @Column(name="walk_count")
    private Long walkCount;
    @Column(name="walk_calorie")
    private Long walkCalorie;
    @Column(name="walk_record_filename")
    private String walkRecordFilename;

    @Column(name="walk_start_time")
    private Time walkStartTime;
    @Column(name="walk_end_time")
    private Time walkEndTime;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn (name = "user_id")
    private User user;

}
