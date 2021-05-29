package com.pika.learning.service;

import com.alibaba.fastjson.JSON;
import com.pika.framework.domain.course.CourseBase;
import com.pika.framework.domain.course.CoursePub;
import com.pika.framework.domain.course.Teachplan;
import com.pika.framework.domain.course.TeachplanMediaPub;
import com.pika.framework.domain.learning.PikaLearningCourse;
import com.pika.framework.domain.learning.PikaLearningList;
import com.pika.framework.domain.learning.ext.PikaLearningCourseExt;
import com.pika.framework.domain.learning.ext.PikaLearningListExt;
import com.pika.framework.domain.learning.response.GetMediaResult;
import com.pika.framework.domain.learning.response.LearningCode;
import com.pika.framework.exception.ExceptionCast;
import com.pika.framework.model.response.CommonCode;
import com.pika.framework.model.response.ResponseResult;
import com.pika.learning.client.CourseClient;
import com.pika.learning.client.CourseSearchClient;
import com.pika.learning.dao.PikaLearningCourseRepository;
import com.pika.learning.dao.PikaLearningListRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class LearningService {

    @Autowired
    CourseSearchClient courseSearchClient;

    @Autowired
    private PikaLearningCourseRepository pikaLearningCourseRepository;

    @Autowired
    private PikaLearningListRepository pikaLearningListRepository;

    @Autowired
    CourseClient courseClient;

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

    /**
     * 添加学习记录
     *
     * @param userId
     * @param courseId
     * @param teachplanId
     * @return
     */
    @Transactional
    public ResponseResult addLearning(String userId, String courseId, String teachplanId) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(courseId) || StringUtils.isEmpty(teachplanId)) {
            return new ResponseResult(CommonCode.FAIL);
        }
        //删除该用户该课程之前的学习记录
        pikaLearningCourseRepository.deleteByUserIdAndCourseId(userId, courseId);
        PikaLearningCourse pikaLearningCourse = new PikaLearningCourse();
        pikaLearningCourse.setCourseId(courseId);
        pikaLearningCourse.setUserId(userId);
        pikaLearningCourse.setTeachplanId(teachplanId);
        pikaLearningCourse.setStartTime(new Date());
        //添加该用户新的学习记录
        pikaLearningCourseRepository.save(pikaLearningCourse);
        return new ResponseResult(CommonCode.SUCCESS);
    }


    /**
     * 获取用户学习过的视频
     *
     * @param userId
     * @return
     */
    public List<PikaLearningCourseExt> getLearningCourseList(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        //查找学习记录
        List<PikaLearningCourse> list = pikaLearningCourseRepository.findByUserIdOrderByStartTime(userId);
        List<PikaLearningCourseExt> dataList = new ArrayList<>();
        for (PikaLearningCourse pikaLearningCourse : list) {
            PikaLearningCourseExt pikaLearningCourseExt = new PikaLearningCourseExt();
            BeanUtils.copyProperties(pikaLearningCourse, pikaLearningCourseExt);
            //获取课程信息
            CourseBase courseBase = courseClient.getCourseBase(pikaLearningCourse.getCourseId());
            pikaLearningCourseExt.setCourseName(courseBase.getName());
            //获取章节信息
            Teachplan teachplan = courseClient.getTeachplan(pikaLearningCourse.getTeachplanId());
            pikaLearningCourseExt.setTeachplanName(teachplan.getPname());
            dataList.add(pikaLearningCourseExt);
        }
        return dataList;
    }

    /**
     * 收藏课程
     *
     * @param userId
     * @param courseId
     * @return
     */
    public ResponseResult collectCourse(String userId, String courseId) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(courseId)) {
            return new ResponseResult(CommonCode.FAIL);
        }
        //查询课程是否已经收藏
        List<PikaLearningList> list = pikaLearningListRepository.findByUserIdAndCourseId(userId, courseId);
        if (list.size() > 0) {
            //提示课程已经收藏
            return new ResponseResult(LearningCode.LEARNING_COURSE_EXISIT);
        }
        PikaLearningList pikaLearningList = new PikaLearningList();
        pikaLearningList.setCourseId(courseId);
        pikaLearningList.setUserId(userId);
        pikaLearningList.setStartTime(new Date());
        pikaLearningList.setStatus("0");
        pikaLearningListRepository.save(pikaLearningList);
        return new ResponseResult();
    }

    /**
     * 获取收藏列表
     * @param userId
     * @return
     */
    public List<PikaLearningListExt> getCollectCourseList(String userId) {
        List<PikaLearningList> list = pikaLearningListRepository.findByUserIdOrderByStartTime(userId);
        if(list.size()<=0){
            return null;
        }
        List<PikaLearningListExt> dataList = new ArrayList<>();
        for (PikaLearningList pikaLearningList : list) {
            CoursePub coursePub = courseClient.getCoursePub(pikaLearningList.getCourseId());
            PikaLearningListExt pikaLearningListExt = new PikaLearningListExt();
            BeanUtils.copyProperties(pikaLearningList,pikaLearningListExt);
            pikaLearningListExt.setCourseName(coursePub.getName());
            pikaLearningListExt.setPic(coursePub.getPic());
            dataList.add(pikaLearningListExt);
        }
        return dataList;
    }

    /**
     * 取消收藏课程
     */
    @Transactional
    public ResponseResult delectCollect(String userId, String courseId) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(courseId)) {
            return new ResponseResult(CommonCode.FAIL);
        }
        pikaLearningListRepository.deleteByUserIdAndCourseId(userId, courseId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 推荐课程
     * @return
     */
    public  List<CoursePub> recommendCourse(String userId){
        HashSet<String> hashSet = new HashSet();
        //获取用户学习视频
        List<PikaLearningCourse>  learningLists= pikaLearningCourseRepository.findByUserIdOrderByStartTime(userId);
        //获取用户收藏视频
        List<PikaLearningList> collectLists = pikaLearningListRepository.findByUserIdOrderByStartTime(userId);
        //根据用户学习视频和收藏视频分类获取视频信息
        if(learningLists.size()>0){
            for (PikaLearningCourse learningList : learningLists) {
                hashSet.add(learningList.getCourseId());
            }
        }
        if(collectLists.size()>0){
            for (PikaLearningList collectList : collectLists) {
                hashSet.add(collectList.getCourseId());
            }
        }
        //将set集合转换为json
        String idsString = JSON.toJSONString(hashSet);
        //获取推荐课程
        List<CoursePub> coursePubs = courseClient.recommendCourse(idsString);
        //替换课程分类
        for (CoursePub coursePub : coursePubs) {
            String mtName = courseClient.getCategoryById(coursePub.getMt()).getLabel();
            String stName = courseClient.getCategoryById(coursePub.getSt()).getLabel();
            coursePub.setMt(mtName);
            coursePub.setSt(stName);
        }
        return coursePubs;
    }



}
