package com.pika.manage_course.controller;

import com.pika.api.course.CourseControllerApi;
import com.pika.framework.domain.course.CourseBase;
import com.pika.framework.domain.course.CourseMarket;
import com.pika.framework.domain.course.Teachplan;
import com.pika.framework.domain.course.ext.CategoryNode;
import com.pika.framework.domain.course.ext.CourseInfo;
import com.pika.framework.domain.course.ext.TeachplanNode;
import com.pika.framework.domain.course.request.CourseListRequest;
import com.pika.framework.model.response.QueryResponseResult;
import com.pika.framework.model.response.ResponseResult;
import com.pika.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {
    @Autowired
    CourseService courseService;



    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }

    @Override
    @GetMapping("/category/list")
    public CategoryNode findCategoryList() {
        System.out.println();
        return courseService.findCategoryList();
    }

    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable("page") int page, @PathVariable("size") int size, CourseListRequest courseListRequest) {
        return courseService.findCourseList(page, size, courseListRequest);
    }

    @Override
    @GetMapping("/getCourseView/{courseId}")
    public CourseBase getCourseBaseById(@PathVariable("courseId") String courseId) {
        return courseService.getCourseBaseById(courseId);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseResult updateCourseBase(@PathVariable("id") String id,@RequestBody CourseBase courseBase) {
        System.out.println(courseBase);
        return courseService.updateCourseBase(id, courseBase);
    }

    @Override
    @GetMapping("/getMarketView/{courseId}")
    public CourseMarket getCourseMarketById(@PathVariable("courseId") String courseId) {
        return courseService.getCourseMarketById(courseId);
    }

    @Override
    @PutMapping("/updateCourseMarket/{id}")
    public ResponseResult updateCourseMarket(@PathVariable("id") String id, @RequestBody CourseMarket courseMarket) {
        return courseService.updateCourseMarket(id, courseMarket);
    }


}
