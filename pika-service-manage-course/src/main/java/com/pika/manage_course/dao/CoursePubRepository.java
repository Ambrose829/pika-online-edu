package com.pika.manage_course.dao;

import com.pika.framework.domain.course.CoursePub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator.
 */
public interface CoursePubRepository extends JpaRepository<CoursePub,String> {

    List<CoursePub> findByGradeAndMtAndSt(String grade,String mt,String st);

    List<CoursePub> findByMtAndSt(String mt,String st);

    List<CoursePub> findByGradeAndMt(String grade,String mt);

    List<CoursePub> findByGradeOrderByPubTime(String grade);
}
