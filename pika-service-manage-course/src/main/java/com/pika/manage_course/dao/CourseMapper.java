package com.pika.manage_course.dao;

import com.github.pagehelper.Page;
import com.pika.framework.domain.course.CourseBase;
import com.pika.framework.domain.course.ext.CategoryNode;
import com.pika.framework.domain.course.ext.CourseInfo;
import com.pika.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@Mapper
public interface CourseMapper {
    CourseBase findCourseBaseById(String id);
    //
    Page<CourseInfo> findCourseListPage(CourseListRequest courseListRequest);

    //课程分类查询
    CategoryNode selectCategoryList();


}
