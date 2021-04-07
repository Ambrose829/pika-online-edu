package com.pika.learning.service;

import com.pika.framework.domain.course.TeachplanMediaPub;
import com.pika.framework.domain.learning.response.GetMediaResult;
import com.pika.framework.domain.learning.response.LearningCode;
import com.pika.framework.exception.ExceptionCast;
import com.pika.framework.model.response.CommonCode;
import com.pika.learning.client.CourseSearchClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class LearningService {

    @Autowired
    CourseSearchClient courseSearchClient;

    //获取课程学习地址（视频播放地址）
    public GetMediaResult getmedia(String courseId, String teachplanId) {
        //校验学生的学生权限...

        //远程调用搜索服务查询课程计划所对应的课程媒资信息
        TeachplanMediaPub teachplanMediaPub = courseSearchClient.getmedia(teachplanId);
        if(teachplanMediaPub == null || StringUtils.isEmpty(teachplanMediaPub.getMediaUrl())){
            //获取学习地址错误
            ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
        }
        return new GetMediaResult(CommonCode.SUCCESS,teachplanMediaPub.getMediaUrl());
    }
}
