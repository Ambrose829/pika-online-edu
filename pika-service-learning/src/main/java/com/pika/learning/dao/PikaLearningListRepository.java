package com.pika.learning.dao;


import com.pika.framework.domain.learning.PikaLearningList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yjw
 * @date 2020/5/18 1:06
 */
public interface PikaLearningListRepository extends JpaRepository<PikaLearningList,String> {


    List<PikaLearningList> findByUserIdAndCourseId(String userId, String courseId);

    int deleteByUserIdAndCourseId(String userId, String courseId);

    List<PikaLearningList> findByUserIdOrderByStartTime(String userId);


}
