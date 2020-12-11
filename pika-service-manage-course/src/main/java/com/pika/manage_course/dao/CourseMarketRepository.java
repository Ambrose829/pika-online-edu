package com.pika.manage_course.dao;

import com.pika.framework.domain.course.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pika
 * @create 2020/11/9
 * @description
 */
public interface CourseMarketRepository extends JpaRepository<CourseMarket, String> {

}
