package com.pika.manage_course.dao;

import com.pika.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
public interface TeachplanRepository extends JpaRepository<Teachplan, String> {

    //根据课程id和parentId查询teachplan
    List<Teachplan> findByCourseidAndParentid(String courseId, String parentId);

}
