package com.pika.learning.dao;


import com.pika.framework.domain.learning.PikaLearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yjw
 * @date 2020/5/18 1:01
 */
public interface PikaLearningCourseRepository extends JpaRepository<PikaLearningCourse,String> {

        List<PikaLearningCourse> findByUserIdOrderByStartTime(String userId);

        int deleteByUserIdAndCourseId(String userId, String courseId);

}


