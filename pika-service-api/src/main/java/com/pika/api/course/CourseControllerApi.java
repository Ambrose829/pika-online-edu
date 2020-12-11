package com.pika.api.course;

import com.pika.framework.domain.course.CourseBase;
import com.pika.framework.domain.course.CourseMarket;
import com.pika.framework.domain.course.Teachplan;
import com.pika.framework.domain.course.ext.CategoryNode;
import com.pika.framework.domain.course.ext.CourseInfo;
import com.pika.framework.domain.course.ext.TeachplanNode;
import com.pika.framework.domain.course.request.CourseListRequest;
import com.pika.framework.model.response.QueryResponseResult;
import com.pika.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@Api(value = "课程管理接口", tags = {"对于课程的增删改查"})
public interface CourseControllerApi {
    @ApiOperation("课程计划查询")
    TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("课程分类查询")
    CategoryNode findCategoryList();

    @ApiOperation("查询我的课程列表")
    QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation("查询课程信息")
    CourseBase getCourseBaseById(String courseId);

    @ApiOperation("更新课程基础信息")
    ResponseResult updateCourseBase(String id, CourseBase courseBase);

    @ApiOperation("获取课程营销信息")
    CourseMarket getCourseMarketById(String courseId);


    @ApiOperation("更新课程营销信息")
    ResponseResult updateCourseMarket(String id, CourseMarket courseMarket);

}
