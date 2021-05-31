package com.example.demo.course.model.dto;

import java.awt.*;
import java.sql.Time;

public interface CourseThemeInfo
{

    Long getCourseId();
    String getCourseName();
    String getCourseTitle();
    String getCourseAddress();
    String getCourseFileName();
    Float getCourseDistance();
    Time getCourseTime();
    String getLatitude();
    String getLongitude();
    String getCourseContents();
    String getCourseRouteInfo();

}
