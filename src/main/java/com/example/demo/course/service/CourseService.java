package com.example.demo.course.service;

import com.example.demo.course.model.dto.CourseThemeInfo;
import com.example.demo.course.repository.CourseRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;

    }


    @Transactional
    public List<CourseThemeInfo> getCourseThemeInfos(String code){
        return courseRepository.findThemaByCode(code);
    }

    @Transactional
    public CourseThemeInfo getCourseThemaInfo(Long courseId){
        return courseRepository.findThemeById(courseId);
    }



    @Transactional
    public List<CourseThemeInfo>getThemePoint(String type){
        return courseRepository.findTemeInfo(type);
    }

    @Transactional
    public List<CourseThemeInfo>getCoursePoint(Long id){
        return courseRepository.findCourseInfo(id);
    }

    @Transactional
    public List<CourseThemeInfo>getCourseList(){
        return courseRepository.findCourseInfobyCode();
    }
}
