package com.example.demo.course.model.dto;

import javax.persistence.*;
import java.awt.*;
import java.sql.Time;

@Entity
@Table(name="course")

public class Course{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="thema_code")
    private String themaCode;

    @Column(name="course_name")
    private String courseName;

    @Column(name="course_title")
    private String courseTitle;

    @Column(name="course_address")
    private String courseAddress;

    @Column(name="course_filename")
    private String courseFilename;

    @Column(name="course_distance")
    private double courseDistance;

    @Column(name="like_count")
    private Long lkteCount;

    @Column(name="course_point")
    private Point coursePoint;

    @Column(name="course_yn")
    private String courseYN;

    @Column(name="course_time")
    private Time courseTime;

    @Column(name="course_contents")
    private String courseContents;

    @Column(name="course_route_info")
    private String courseRouteInfo;
}
