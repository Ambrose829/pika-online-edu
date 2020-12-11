package com.pika.framework.domain.course.ext;

import com.pika.framework.domain.course.CourseBase;
import lombok.Data;
import lombok.ToString;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@Data
@ToString
public class CourseInfo extends CourseBase {
    //课程图片
    private String pic;
}
