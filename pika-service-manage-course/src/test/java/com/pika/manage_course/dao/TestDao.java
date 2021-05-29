package com.pika.manage_course.dao;

import com.pika.framework.domain.course.CourseBase;

import com.pika.framework.domain.course.ext.TeachplanNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeachplanMapper teachplanMapper;


    @Test
    public void testCourseBaseRepository(){
        Optional<CourseBase> optional = courseBaseRepository.findById("402885816240d276016240f7e5000002");
        if(optional.isPresent()){
            CourseBase courseBase = optional.get();
            System.out.println(courseBase);
        }

    }

//    @Test
//    public void testCourseMapper(){
//        CourseBase courseBase = courseMapper.findCourseBaseById("402885816240d276016240f7e5000002");
//        System.out.println(courseBase);
//
//    }
    @Test
    public void testFindTeachplan(){
        TeachplanNode teachplanNode = teachplanMapper.selectList("4028e581617f945f01617f9dabc40000");
        System.out.println(teachplanNode);
    }

    @Test
    public void testPageHelper() {
        //查询第一页，每页显示10条记录
//        PageHelper.startPage(1, 10);
//        Page<CourseBase> courseList = courseMapper.findCourseList();
//        List<CourseBase> result = courseList.getResult();
//        long total = courseList.getTotal();
        System.out.println();
    }

//    @Test
//    public void testDictionary() {
//        PikaDictionary bydType = sysDicthinaryRepository.findBydType("200");
//        System.out.println(bydType);
//    }
}
