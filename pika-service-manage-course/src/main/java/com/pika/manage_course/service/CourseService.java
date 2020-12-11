package com.pika.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pika.framework.domain.course.CourseBase;
import com.pika.framework.domain.course.CourseMarket;
import com.pika.framework.domain.course.Teachplan;
import com.pika.framework.domain.course.ext.CategoryNode;
import com.pika.framework.domain.course.ext.CourseInfo;
import com.pika.framework.domain.course.ext.TeachplanNode;
import com.pika.framework.domain.course.request.CourseListRequest;
import com.pika.framework.exception.ExceptionCast;
import com.pika.framework.model.response.CommonCode;
import com.pika.framework.model.response.QueryResponseResult;
import com.pika.framework.model.response.QueryResult;
import com.pika.framework.model.response.ResponseResult;
import com.pika.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@Service
public class CourseService {

    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanRepository teachplanRepository;
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseMarketRepository courseMarketRepository;



    /**
     * 课程计划查询
     * @param courseId
     * @return
     */
    public TeachplanNode findTeachplanList(String courseId) {
        return teachplanMapper.selectList(courseId);
    }

    /**
     * 添加课程计划
     * @param teachplan
     * @return
     */
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {
        if (teachplan == null || StringUtils.isEmpty(teachplan.getCourseid()) || StringUtils.isEmpty(teachplan.getPname())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        //课程计划
        String courseid = teachplan.getCourseid();
        //处理parentId
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)) {

            //取出该课程的根节点
            parentid = getTeachplanRoot(courseid);
        }
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
//        if (!optional.isPresent()) {
//            //进行失败处理
//        }
        Teachplan parentNode = optional.get();
        //父节点的级别
        String grade = parentNode.getGrade();

        //新节点
        Teachplan teachplanNew = new Teachplan();
        //将页面提交的teachplan信息拷贝到teachplanNew对象之中
        BeanUtils.copyProperties(teachplan, teachplanNew);
        teachplanNew.setParentid(parentid);
        teachplanNew.setCourseid(courseid);
        //设置级别，根据父节点的级别
        if (grade.equals("1")) {
            teachplanNew.setGrade("2");

        } else {
            teachplanNew.setGrade("3");
        }

        teachplanRepository.save(teachplanNew);

        return new ResponseResult(CommonCode.SUCCESS);

    }

    /**
     * 查询课程分类信息
     * @return
     */
    public CategoryNode findCategoryList() {
        return courseMapper.selectCategoryList();
    }

    /**
     * 分页查询课程列表
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest) {
        PageHelper.startPage(page, size);
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(courseListPage.getResult());//数据列表
        queryResult.setTotal(courseListPage.getTotal());//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }

    /**
     * 根据课程号查询课程
     * @param courseId
     * @return
     */
    public CourseBase getCourseBaseById(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (optional.isPresent()) {
            CourseBase courseBase = optional.get();
            return courseBase;
        }
        return null;
    }

    /**
     * 修改更新课程信息
     * @param id
     * @param courseBase
     * @return
     */
    public ResponseResult updateCourseBase(String id, CourseBase courseBase) {
        CourseBase courseBaseById = this.getCourseBaseById(id);
        if (courseBaseById != null) {
            courseBaseById.setName(courseBase.getName());
            courseBaseById.setUsers(courseBase.getUsers());

            courseBaseById.setSt(courseBase.getSt());
            courseBaseById.setMt(courseBase.getMt());

            courseBaseById.setGrade(courseBase.getGrade());
            courseBaseById.setStudymodel(courseBase.getStudymodel());
            courseBaseById.setDescription(courseBase.getDescription());

            courseBaseRepository.save(courseBaseById);
            return new ResponseResult(CommonCode.SUCCESS);
        }

        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * 查询课程营销信息
     * @param courseId
     * @return
     */
    public CourseMarket getCourseMarketById(String courseId) {

        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if (optional.isPresent()) {
            CourseMarket courseMarket = optional.get();
            return courseMarket;
        }
        return null;
    }

    /**
     * 更新课程营销信息
     * @param id
     * @param courseMarket
     * @return
     */
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket) {
        CourseMarket courseMarketById = this.getCourseMarketById(id);
        if (courseMarketById != null) {
            String charge = courseMarket.getCharge();
            courseMarketById.setCharge(charge);
            courseMarketById.setPrice(courseMarket.getPrice());

            String valid = courseMarket.getValid();
            courseMarketById.setValid(valid);

            courseMarketById.setStartTime(courseMarket.getStartTime());
            courseMarketById.setEndTime(courseMarket.getEndTime());
            courseMarketById.setQq(courseMarket.getQq());
            courseMarketRepository.save(courseMarketById);
            return new ResponseResult(CommonCode.SUCCESS);
        }

        return new ResponseResult(CommonCode.FAIL);
    }





    /**
     * 获取课程的根节点id
     * @param courseId
     * @return 课程的根节点
     */
    private String getTeachplanRoot (String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent())  {
            return null;
        }
        //课程信息
        CourseBase courseBase = optional.get();

        //查询课程根节点
        List<Teachplan> teachplanList  = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if (teachplanList == null || teachplanList.size() <= 0) {
            //查询不到，要自动添加根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setPname(courseBase.getName());
            teachplan.setCourseid(courseId);
            teachplan.setStatus("0");
            teachplanRepository.save(teachplan);
            return teachplan.getId();
        }
        return teachplanList.get(0).getId();
    }
}
