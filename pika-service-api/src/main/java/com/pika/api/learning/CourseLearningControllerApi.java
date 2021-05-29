package com.pika.api.learning;

import com.pika.framework.domain.course.CoursePub;
import com.pika.framework.domain.learning.ext.PikaLearningCourseExt;
import com.pika.framework.domain.learning.ext.PikaLearningListExt;
import com.pika.framework.domain.learning.response.GetMediaResult;
import com.pika.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * Created by Administrator.
 */
@Api(value = "录播课程学习管理",description = "录播课程学习管理")
public interface CourseLearningControllerApi {

    @ApiOperation("获取课程学习地址")
    public GetMediaResult getmedia(String courseId, String teachplanId);

    @ApiOperation("添加课程学习进度")
    ResponseResult addLearning(String userId, String courseId, String teachplanId);


    @ApiOperation("获取用户学习的视频")
    List<PikaLearningCourseExt> getLearningCourseList(String userId);

    @ApiOperation("收藏课程")
    ResponseResult collectCourse(String userId,String courseId);

    @ApiOperation("获取收藏课程列表")
    List<PikaLearningListExt> getCollectCourseList(String userId);

    @ApiOperation("取消收藏")
    ResponseResult deleteCollect(String userId,String courseId);

    @ApiOperation("推荐课程")
    List<CoursePub> recommendCourse(String userId);
}
