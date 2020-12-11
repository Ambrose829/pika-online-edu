package com.pika.manage_course.dao;

import com.pika.framework.domain.course.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pika
 * @create 2020/11/8
 * @description 使用jpa接口
 */
public interface CourseBaseRepository extends JpaRepository<CourseBase, String> {


}
