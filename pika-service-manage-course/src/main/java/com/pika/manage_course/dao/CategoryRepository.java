package com.pika.manage_course.dao;

import com.pika.framework.domain.course.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,String> {

}
