package com.example.demo.course.repository;

import com.example.demo.course.model.dto.CourseThemeInfo;
import com.example.demo.course.model.dto.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository  extends JpaRepository<Course,Long> {



//    Long getCourseId();
//    String getCourseName();
//    String getCourseTitle();
//    String getCourseAddress();
//    String getCourseFileName();
//    Double getCourseDistance();
//    Time getCourseTime();
//    Point getCoursePoint();

    @Query(value="select c.id as CourseId, c.course_name as CourseName, c.course_title as CourseTitle" +
            " , c.course_address as CourseAddress, c.course_filename as CourseFileName, c.course_distance as CourseDistance, c.course_time as CourseTime, c.course_point as CoursePoint" +
            " from course c where c.thema_code=:code", nativeQuery = true)
    List<CourseThemeInfo> findThemaByCode(String code);


    @Query(value="select c.id as CourseId, c.course_name as CourseName, c.course_title as CourseTitle," +
            "c.course_address as CourseAddress, " +
            "c.course_filename as CourseFileName, c.course_distance as CourseDistance, " +
            "c.course_time as CourseTime, c.course_point as CoursePoint," +
            "c.course_contents as CourseContents, c.course_route_info as CourseRouteInfo" +
            " from course c where c.id=:courseId",nativeQuery = true)
    CourseThemeInfo findThemeById(Long courseId);


    @Query(value="select c.id as CourseId, c.course_name as CourseName, c.course_address as CourseAddress, c.course_filename as CourseFileName," +
            "c.course_time as CourseTime from course c where c.thema_code ='000'",nativeQuery = true)
    List <CourseThemeInfo>  findCourseInfobyCode();

    @Query(value=" SELECT theme_route_name as CourseName, ST_X(theme_route_point) as Latitude, ST_Y(theme_route_point) as Longitude from theme_route where course_id=:type",nativeQuery = true)
    List<CourseThemeInfo> findTemeInfo(String type);


    @Query(value="SELECT course_name as CourseName, ST_X(course_point) as Latitude, ST_Y(course_point) as Longitude, course_address as CourseAddress from course_route" +
            " where course_id =:id",nativeQuery = true)
    List<CourseThemeInfo> findCourseInfo(Long id);

}
