package com.pika.manage_course.dao;

import com.pika.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Pika
 * @create 2020/11/8
 * @description 课程计划相关mapper
 */
@Mapper
public interface TeachplanMapper {
    //课程计划查询
    TeachplanNode selectList(String courseId);

}
