package com.pika.learning.client;

import com.pika.framework.client.PikaServiceList;

import com.pika.framework.domain.course.Category;
import com.pika.framework.domain.course.CourseBase;
import com.pika.framework.domain.course.CoursePub;
import com.pika.framework.domain.course.Teachplan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = PikaServiceList.PIKA_SERVICE_MANAGE_COURSE)
public interface CourseClient {

    @GetMapping("/course/teachPlan/get/{teachplanId}")
    Teachplan getTeachplan(@PathVariable("teachplanId") String teachplanId);

    @GetMapping("/course/coursebase/get/{courseId}")
    CourseBase getCourseBase(@PathVariable("courseId") String courseId);

    @GetMapping("/course/coursepub/get/{courseId}")
    CoursePub getCoursePub(@PathVariable("courseId") String courseId);

    @GetMapping("/course/recommend/{learnings}")
    List<CoursePub> recommendCourse(@PathVariable("learnings") String learnings);

    @GetMapping("/course/category/get/{id}")
    Category getCategoryById(@PathVariable("id") String id);


}
