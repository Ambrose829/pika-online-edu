package com.pika.manage_course.controller;

import com.pika.api.course.CourseControllerApi;
import com.pika.framework.domain.course.*;
import com.pika.framework.domain.course.ext.CategoryNode;
import com.pika.framework.domain.course.ext.CourseInfo;
import com.pika.framework.domain.course.ext.CourseView;
import com.pika.framework.domain.course.ext.TeachplanNode;
import com.pika.framework.domain.course.request.CourseListRequest;
import com.pika.framework.domain.course.response.CoursePublishResult;
import com.pika.framework.model.response.QueryResponseResult;
import com.pika.framework.model.response.ResponseResult;
import com.pika.framework.utils.PikaOauth2Util;
import com.pika.framework.web.BaseController;
import com.pika.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController implements CourseControllerApi {
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

//    @Override
//    @GetMapping("/category/list")
//    public CategoryNode findCategoryList() {
//        System.out.println();
//        return courseService.findCategoryList();
//    }


    /**
     * 获取课程计划信息
     *
     * @param id
     * @return
     */
    @GetMapping("/teachPlan/get/{id}")
    @Override
    public Teachplan getTeachPlan(@PathVariable("id") String id) {
        return courseService.getTeachPlan(id);
    }

    /**
     * 更新课程计划信息
     *
     * @param teachplan
     * @return
     */
    @PutMapping("/teachPlan/update")
    @Override
    public ResponseResult updateTeachPlan(@RequestBody Teachplan teachplan) {
        return courseService.updateTeachPlan(teachplan);
    }

    /**
     * 删除课程计划信息+
     *
     * @param id
     * @return
     */
    @DeleteMapping("/teachPlan/delete/{id}")
    @Override
    public ResponseResult deleteTeachPlan(@PathVariable("id") String id) {
        return courseService.deleteTeachPlan(id);
    }

    //***************************************************我的课程*************************************************************8

    /**
     * 我的课程-课程列表展示
     * 课程列表信息查询
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    @PreAuthorize("hasAnyAuthority('course_find_list')")
    @GetMapping("/coursebase/list/{page}/{size}")
    @Override
    public QueryResponseResult findCourseList(@PathVariable("page") int page,
                                              @PathVariable("size") int size,
                                              CourseListRequest courseListRequest) {
        PikaOauth2Util pikaOauth2Util = new PikaOauth2Util();
        //从请求头中获取jwt令牌，解析jwt令牌获取用户信息
        PikaOauth2Util.UserJwt userJwtFromHeader = pikaOauth2Util.getUserJwtFromHeader(request);
        //获取用户的教育机构id
//        String companyId = userJwtFromHeader.getCompanyId();
        String companyId = null;
        return courseService.findCourseList(companyId, page, size, courseListRequest);
    }

    /**
     * 我的课程-新增课程
     * 添加课程信息
     *
     * @param courseBase
     * @return
     */
    @PostMapping("/coursebase/add")
    @Override
    public ResponseResult addCourseBase(@RequestBody CourseBase courseBase) {
        return courseService.addCourseBase(courseBase);
    }

    /**
     * 我的课程-管理课程-基本信息
     * 课程信息的查询，数据回显
     *
     * @param courseId
     * @return
     */
    @GetMapping("/coursebase/get/{courseId}")
    @Override
    public CourseBase getCourseBaseByCourseId(@PathVariable("courseId") String courseId) {
        return courseService.getCourseBaseByCourseId(courseId);
    }

    /**
     * 我的课程-管理课程-基本信息
     * 课程信息的编辑
     *
     * @param id
     * @param courseBase
     * @return
     */
    @PutMapping("/coursebase/update/{id}")
    @Override
    public ResponseResult updateCourseBase(@PathVariable("id") String id, @RequestBody CourseBase courseBase) {
        return courseService.updateCourseBase(id, courseBase);
    }

    /**
     * 我的课程-管理课程-课程营销
     * 获取课程营销信息
     *
     * @param courseId
     * @return
     */
    @GetMapping("/coursemarket/get/{courseId}")
    @Override
    public CourseMarket getCourseMarketById(@PathVariable("courseId") String courseId) {
        return courseService.getCourseMarketById(courseId);
    }

    /**
     * 我的课程-管理课程-课程营销
     * 添加课程营销信息
     *
     * @param courseId
     * @param courseMarket
     * @return
     */
    @PutMapping("/coursemarket/update/{courseId}")
    @Override
    public ResponseResult updateCourseMarket(@PathVariable("courseId") String courseId, @RequestBody CourseMarket courseMarket) {
        return courseService.updateCourseMarket(courseId, courseMarket);
    }

    /**
     * 我的课程-课程图片-添加图片
     *
     * @param courseId
     * @param pic
     * @return
     */
    @PostMapping("/coursepic/add")
    @Override
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic") String pic) {
        return courseService.addCoursePic(courseId, pic);
    }

    /**
     * 根据课程id获取课程图片信息
     *
     * @param courseId
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('course_find_pic')")
    @GetMapping("/coursepic/list/{courseId}")
    @Override
    public CoursePic findCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.findCoursePic(courseId);
    }

    /**
     * 根据课程id删除课程图片信息
     * 不删除图片文件，只删除课程图片记录
     *
     * @param courseId
     * @return
     */
    @DeleteMapping("/coursepic/delete")
    @Override
    public ResponseResult deleteCoursePic(@RequestParam("courseId") String courseId) {
        return courseService.deleteCoursePic(courseId);
    }

    /**
     * 根据课程id加载课程详情数据
     *
     * @param id
     * @return
     */
    @GetMapping("/courseview/{id}")
    @Override
    public CourseView getCourseView(@PathVariable("id") String id) {
        return courseService.getCourseView(id);
    }

    /**
     * 课程详情界面预览
     *
     * @param id
     * @return
     */
    @PostMapping("/preview/{id}")
    @Override
    public CoursePublishResult previewCourse(@PathVariable("id") String id) {
        return courseService.previewCourse(id);
    }

    /**
     * 课程详情界面发布
     * 根据课程id创建页面信息，然后远程调用cms发布页面，返回页面url
     *
     * @param id
     * @return1
     */
    @PostMapping("/publish/{id}")
    @Override
    public CoursePublishResult publish(@PathVariable("id") String id) {
        return courseService.publish(id);
    }

    /**
     * 将课程计划与媒资绑定，保存到数据库
     *
     * @param teachplanMedia
     * @return
     */
    @PostMapping("/savemedia")
    @Override
    public ResponseResult saveMedia(@RequestBody TeachplanMedia teachplanMedia) {
        return courseService.saveMedia(teachplanMedia);
    }

    /**
     * 获取课程发布信息
     * @param courseId
     * @return
     */
    @GetMapping("/coursepub/get/{courseId}")
    public CoursePub getCoursePub(@PathVariable("courseId") String courseId){
        return courseService.getCoursePub(courseId);
    }

    /**
     * 推荐课程
     * @param learnings
     * @return
     */
    @GetMapping("/recommend/{learnings}")
    public List<CoursePub> recommendCourse(@PathVariable("learnings") String learnings){
        return courseService.recommendCourse(learnings);
    }


}
