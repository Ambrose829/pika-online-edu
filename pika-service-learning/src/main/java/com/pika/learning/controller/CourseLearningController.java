package com.pika.learning.controller;

import com.pika.api.learning.CourseLearningControllerApi;
import com.pika.framework.domain.course.CoursePub;
import com.pika.framework.domain.learning.ext.PikaLearningCourseExt;
import com.pika.framework.domain.learning.ext.PikaLearningListExt;
import com.pika.framework.domain.learning.response.GetMediaResult;
import com.pika.framework.model.response.ResponseResult;
import com.pika.learning.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("/learning/course")
public class CourseLearningController implements CourseLearningControllerApi {

    @Autowired
    LearningService learningService;

    @Override
    @GetMapping("/getmedia/{courseId}/{teachplanId}")
    public GetMediaResult getmedia(@PathVariable("courseId") String courseId,
                                   @PathVariable("teachplanId")String teachplanId) {

        return learningService.getmedia(courseId,teachplanId);
    }

    /**
     * 添加学习进度
     *
     * @param userId
     * @param courseId
     * @param teachplanId
     * @return
     */
    @PostMapping("/addlearning/{userId}/{courseId}/{teachplanId}")
    @Override
    public ResponseResult addLearning(@PathVariable("userId") String userId,
                                      @PathVariable("courseId") String courseId,
                                      @PathVariable("teachplanId") String teachplanId) {
        return learningService.addLearning(userId, courseId, teachplanId);
    }

    /**
     * 获取用户学习过的视频
     *
     * @param userId
     * @return
     */
    @GetMapping("/getlearning/{userId}")
    @Override
    public List<PikaLearningCourseExt> getLearningCourseList(@PathVariable("userId") String userId) {
        return learningService.getLearningCourseList(userId);
    }

    /**
     * 收藏课程
     *
     * @param userId
     * @param courseId
     * @return
     */
    @PostMapping("/collect/{userId}/{courseId}")
    @Override
    public ResponseResult collectCourse(@PathVariable("userId") String userId, @PathVariable("courseId") String courseId) {
        return learningService.collectCourse(userId, courseId);
    }

    /**
     * 获取用户收藏的课程
     *
     * @param userId
     * @return
     */
    @GetMapping("/getcollect/{userId}")
    @Override
    public List<PikaLearningListExt> getCollectCourseList(@PathVariable("userId") String userId) {
        return learningService.getCollectCourseList(userId);
    }

    /**
     * 取消收藏
     *
     * @param userId
     * @param courseId
     * @return
     */
    @PostMapping("/deletecollect/{userId}/{courseId}")
    @Override
    public ResponseResult deleteCollect(@PathVariable("userId") String userId, @PathVariable("courseId") String courseId) {
        return learningService.delectCollect(userId, courseId);
    }

    /**
     * 推荐课程
     *
     * @param userId
     * @return
     */
    @GetMapping("/recommend/{userId}")
    @Override
    public List<CoursePub> recommendCourse(@PathVariable("userId") String userId) {
        return learningService.recommendCourse(userId);
    }
}
