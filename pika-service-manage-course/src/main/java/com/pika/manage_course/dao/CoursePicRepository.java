package com.pika.manage_course.dao;

import com.pika.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator.
 */
public interface CoursePicRepository extends JpaRepository<CoursePic,String> {
    //当返回值大于0，表示删除成功的记录数
    long deleteByCourseid(String courseId);
}
