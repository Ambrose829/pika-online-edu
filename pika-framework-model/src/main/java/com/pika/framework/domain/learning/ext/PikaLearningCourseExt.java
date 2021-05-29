package com.pika.framework.domain.learning.ext;

import com.pika.framework.domain.learning.PikaLearningCourse;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class PikaLearningCourseExt extends PikaLearningCourse {

    private String courseName;

    private String teachplanName;


}
