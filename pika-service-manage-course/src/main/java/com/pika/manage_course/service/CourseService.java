package com.pika.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pika.framework.domain.cms.CmsPage;
import com.pika.framework.domain.cms.response.CmsCode;
import com.pika.framework.domain.cms.response.CmsPageResult;
import com.pika.framework.domain.cms.response.CmsPostPageResult;
import com.pika.framework.domain.course.*;
import com.pika.framework.domain.course.ext.CategoryNode;
import com.pika.framework.domain.course.ext.CourseInfo;
import com.pika.framework.domain.course.ext.CourseView;
import com.pika.framework.domain.course.ext.TeachplanNode;
import com.pika.framework.domain.course.request.CourseListRequest;
import com.pika.framework.domain.course.response.CourseCode;
import com.pika.framework.domain.course.response.CoursePublishResult;
import com.pika.framework.exception.ExceptionCast;
import com.pika.framework.model.response.CommonCode;
import com.pika.framework.model.response.QueryResponseResult;
import com.pika.framework.model.response.QueryResult;
import com.pika.framework.model.response.ResponseResult;
import com.pika.manage_course.client.CmsPageClient;
import com.pika.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    CoursePubRepository coursePubRepository;
    @Autowired
    TeachplanMediaRepository teachplanMediaRepository;
    @Autowired
    TeachplanMediaPubRepository teachplanMediaPubRepository;
    @Autowired
    CoursePicRepository coursePicRepository;
    @Autowired
    CmsPageClient cmsPageClient;

    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;


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
     * 查询课程营销信息
     * @param courseId
     * @return
     */
    public CourseMarket getCourseMarketById(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            //返回异常，提示课程id为空
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
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

    //根据id查询课程基本信息
    public CourseBase findCourseBaseById(String courseId){
        Optional<CourseBase> baseOptional = courseBaseRepository.findById(courseId);
        if(baseOptional.isPresent()){
            CourseBase courseBase = baseOptional.get();
            return courseBase;
        }
        ExceptionCast.cast(CourseCode.COURSE_DENIED_DELETE);
        return null;
    }




    //向teachplanMediaPub中保存课程媒资信息
    private void saveTeachplanMediaPub(String courseId){
        //先删除teachplanMediaPub中的数据
        teachplanMediaPubRepository.deleteByCourseId(courseId);
        //从teachplanMedia中查询
        List<TeachplanMedia> teachplanMediaList = teachplanMediaRepository.findByCourseId(courseId);
        List<TeachplanMediaPub> teachplanMediaPubs = new ArrayList<>();
        //将teachplanMediaList数据放到teachplanMediaPubs中
        for(TeachplanMedia teachplanMedia:teachplanMediaList){
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            BeanUtils.copyProperties(teachplanMedia,teachplanMediaPub);
            //添加时间戳
            teachplanMediaPub.setTimestamp(new Date());
            teachplanMediaPubs.add(teachplanMediaPub);
        }

        //将teachplanMediaList插入到teachplanMediaPub
        teachplanMediaPubRepository.saveAll(teachplanMediaPubs);
    }

//    //将coursePub对象保存到数据库
//    private CoursePub saveCoursePub(String id, CoursePub coursePub){
//
//        CoursePub coursePubNew = null;
//        //根据课程id查询coursePub
//        Optional<CoursePub> coursePubOptional = coursePubRepository.findById(id);
//        if(coursePubOptional.isPresent()){
//            coursePubNew = coursePubOptional.get();
//        }else{
//            coursePubNew = new CoursePub();
//        }
//
//        //将coursePub对象中的信息保存到coursePubNew中
//        BeanUtils.copyProperties(coursePub,coursePubNew);
//        coursePubNew.setId(id);
//        //时间戳,给logstach使用
//        coursePubNew.setTimestamp(new Date());
//        //发布时间
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
//        String date = simpleDateFormat.format(new Date());
//        coursePubNew.setPubTime(date);
//        coursePubRepository.save(coursePubNew);
//        return coursePubNew;
//    }
    //创建coursePub对象
//    private CoursePub createCoursePub(String id){
//        CoursePub coursePub = new CoursePub();
//        //根据课程id查询course_base
//        Optional<CourseBase> baseOptional = courseBaseRepository.findById(id);
//        if(baseOptional.isPresent()){
//            CourseBase courseBase = baseOptional.get();
//            //将courseBase属性拷贝到CoursePub中
//            BeanUtils.copyProperties(courseBase,coursePub);
//        }
//
//        //查询课程图片
//        Optional<CoursePic> picOptional = coursePicRepository.findById(id);
//        if(picOptional.isPresent()){
//            CoursePic coursePic = picOptional.get();
//            BeanUtils.copyProperties(coursePic, coursePub);
//        }
//
//        //课程营销信息
//        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(id);
//        if(marketOptional.isPresent()){
//            CourseMarket courseMarket = marketOptional.get();
//            BeanUtils.copyProperties(courseMarket, coursePub);
//        }
//
//        //课程计划信息
//        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
//        String jsonString = JSON.toJSONString(teachplanNode);
//        //将课程计划信息json串保存到 course_pub中
//        coursePub.setTeachplan(jsonString);
//        return coursePub;
//
//    }

    //更新课程状态为已发布 202002
    private CourseBase  saveCoursePubState(String courseId){
        CourseBase courseBaseById = this.findCourseBaseById(courseId);
        courseBaseById.setStatus("202002");
        courseBaseRepository.save(courseBaseById);
        return courseBaseById;
    }

    //保存课程计划与媒资文件的关联信息
    public ResponseResult savemedia(TeachplanMedia teachplanMedia) {
        if(teachplanMedia == null || StringUtils.isEmpty(teachplanMedia.getTeachplanId())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //校验课程计划是否是3级
        //课程计划
        String teachplanId = teachplanMedia.getTeachplanId();
        //查询到课程计划
        Optional<Teachplan> optional = teachplanRepository.findById(teachplanId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //查询到教学计划
        Teachplan teachplan = optional.get();
        //取出等级
        String grade = teachplan.getGrade();
        if(StringUtils.isEmpty(grade) || !grade.equals("3")){
            //只允许选择第三级的课程计划关联视频
            ExceptionCast.cast(CourseCode.COURSE_MEDIA_TEACHPLAN_GRADEERROR);
        }
        //查询teachplanMedia
        Optional<TeachplanMedia> mediaOptional = teachplanMediaRepository.findById(teachplanId);
        TeachplanMedia one = null;
        if(mediaOptional.isPresent()){
            one = mediaOptional.get();
        }else{
            one = new TeachplanMedia();
        }

        //将one保存到数据库
        one.setCourseId(teachplan.getCourseid());//课程id
        one.setMediaId(teachplanMedia.getMediaId());//媒资文件的id
        one.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());//媒资文件的原始名称
        one.setMediaUrl(teachplanMedia.getMediaUrl());//媒资文件的url
        one.setTeachplanId(teachplanId);
        teachplanMediaRepository.save(one);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    //查询我的课程
    public QueryResponseResult<CourseInfo> findCourseList(String company_id, int page, int size, CourseListRequest courseListRequest) {
        if(courseListRequest == null){
            courseListRequest = new CourseListRequest();
        }
        //将公司id参数传入dao
        courseListRequest.setCompanyId(company_id);
        //分页
        PageHelper.startPage(page, size);
        //调用dao
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        List<CourseInfo> list = courseListPage.getResult();
        long total = courseListPage.getTotal();
        QueryResult<CourseInfo> courseIncfoQueryResult = new QueryResult<CourseInfo>();
        courseIncfoQueryResult.setList(list);
        courseIncfoQueryResult.setTotal(total);
        return new QueryResponseResult<CourseInfo>(CommonCode.SUCCESS,courseIncfoQueryResult);
    }


    
    public CourseBase findCourseById(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()) {
            //提示查询课程信息为空
            ExceptionCast.cast(CourseCode.COURSE_SELECT_COURSEBASEISNULL);
        }
        return optional.get();
    }

    /**
     * 根据课程id获取课程信息
     * 加载课程树
     *
     * @param courseId
     * @return
     */
    
    public TeachplanNode findTeachPlanList(String courseId) {
        return teachplanMapper.selectList(courseId);
    }

    /**
     * 指定课程添加课程计划
     * 若选中上级节点为空，默认上级节点为根节点
     *
     * @param teachPlan
     * @return
     */
    
    public ResponseResult addTeachPlan(Teachplan teachPlan) {
        if (teachPlan == null || StringUtils.isEmpty(teachPlan.getCourseid())
                || StringUtils.isEmpty(teachPlan.getPname())) {
            //添加课程计划关键信息为空
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_TEACHPLANISNULL);
        }
        //获取课程id和课程父id
        String courseId = teachPlan.getCourseid();
        String parentId = teachPlan.getParentid();
        //如果父id为空，获取课程计划的根节点，即拥有课程名称的记录的id
        if (StringUtils.isEmpty(parentId)) {
            parentId = getParentId(courseId);
        }
        Optional<Teachplan> optional = teachplanRepository.findById(parentId);
        if (!optional.isPresent()) {
            //返回异常，提示查询课程计划信息为空
            ExceptionCast.cast(CourseCode.COURSE_SELECT_PARENTISNULL);
        }
        Teachplan parentTeachplan = optional.get();
        //获取父节点的级别
        String parentGrade = parentTeachplan.getGrade();
        //设置状态为未发布
        teachPlan.setStatus("0");
        teachPlan.setParentid(parentId);
        //根据父节点级别设置新增节点级别
        if ("1".equals(parentGrade)) {
            teachPlan.setGrade("2");
        } else if ("2".equals(parentGrade)) {
            teachPlan.setGrade("3");
        }
        //学习时长最大值
        double timelengthMax = 1000;
        //学习时长超过存储范围，timelength的类型为double(5,2),表示总长度最大为5,小数位最大为2,故最大值为999.99
        if (teachPlan.getTimelength() >= timelengthMax) {
            return new ResponseResult(false, 11111, "请填写合适的学习时长");
        }
        Teachplan add = teachplanRepository.save(teachPlan);
        if (add != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        } else {
            return new ResponseResult(CommonCode.FAIL);
        }
    }

    
    public Teachplan getTeachPlan(String id) {
        Optional<Teachplan> optional = teachplanRepository.findById(id);
        if (!optional.isPresent()) {
            //查询课程计划信息为空
            ExceptionCast.cast(CourseCode.COURSE_SELECT_TEACHPLANISNULL);
        }
        return optional.get();
    }

    
    @Transactional
    public ResponseResult updateTeachPlan(Teachplan teachPlan) {
        if (teachPlan == null || StringUtils.isEmpty(teachPlan.getCourseid())
                || StringUtils.isEmpty(teachPlan.getPname())) {
            //添加课程计划关键信息为空
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_TEACHPLANISNULL);
        }
        //查询更新的课程计划
        Optional<Teachplan> optional = teachplanRepository.findById(teachPlan.getId());
        if (!optional.isPresent()) {
            //返回异常，提示查询课程计划信息为空
            ExceptionCast.cast(CourseCode.COURSE_SELECT_PARENTISNULL);
        }
        String parentId = teachPlan.getParentid();
        //判断父节点是否为空
        if (StringUtils.isEmpty(parentId)) {
            //若父节点为空，获取根节点Id(即有课程名称的根节点id)
            parentId = getParentId(teachPlan.getCourseid());
        }
        //查询父节点课程计划数据
        Optional<Teachplan> optionalParent = teachplanRepository.findById(parentId);
        if (!optionalParent.isPresent()) {
            //返回异常，提示查询课程计划信息为空
            ExceptionCast.cast(CourseCode.COURSE_SELECT_PARENTISNULL);
        }
        Teachplan parentTeachplan = optionalParent.get();
        //获取父节点级别
        String parentGrade = parentTeachplan.getGrade();
        teachPlan.setParentid(parentId);
        //根据父节点级别设置新增节点级别
        if ("1".equals(parentGrade)) {
            teachPlan.setGrade("2");
        } else if ("2".equals(parentGrade)) {
            teachPlan.setGrade("3");
        }
        Teachplan update = teachplanRepository.save(teachPlan);
        if (update != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    
    @Transactional
    public ResponseResult deleteTeachPlan(String id) {
        //根据id查询课程计划信息
        Optional<Teachplan> optional = teachplanRepository.findById(id);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CourseCode.COURSE_SELECT_TEACHPLANISNULL);
        }
        //查询该课程计划是否还有其他子节点
        List<Teachplan> list = teachplanRepository.findByParentid(id);
        if (list.size() == 0 || list == null) {
            //根据id删除课程计划
            teachplanRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(false);
        responseResult.setMessage("此节点下有其他节点，请清空后再操作");
        return responseResult;
    }

    
    @Transactional
    public ResponseResult addCourseBase(CourseBase courseBase) {
        if (courseBase == null || StringUtils.isEmpty(courseBase.getName()) ||
                StringUtils.isEmpty(courseBase.getGrade()) ||
                StringUtils.isEmpty(courseBase.getStudymodel())) {
            //返回异常，提示新增课程关键信息为空
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEBASEISNULL);
        }
        //设置课程状态为未发布:202001
        courseBase.setStatus("202001");
        CourseBase save = courseBaseRepository.save(courseBase);
        if (save != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    
    public CourseBase getCourseBaseByCourseId(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            //返回异常，提示课程id为空
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()) {
            //返回异常，提示查询课程信息为空
            ExceptionCast.cast(CourseCode.COURSE_SELECT_COURSEBASEISNULL);
        }
        return optional.get();
    }

    
    @Transactional
    public ResponseResult updateCourseBase(String id, CourseBase courseBase) {
        if (StringUtils.isEmpty(id) || courseBase == null ||
                StringUtils.isEmpty(courseBase.getName()) ||
                StringUtils.isEmpty(courseBase.getGrade()) ||
                StringUtils.isEmpty(courseBase.getStudymodel())) {
            //返回异常，提示新增课程关键信息为空
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEBASEISNULL);
        }
        //根据id查询课程信息
        Optional<CourseBase> optional = courseBaseRepository.findById(id);
        if (!optional.isPresent()) {
            //返回异常，提示课程信息不存在
            ExceptionCast.cast(CourseCode.COURSE_SELECT_COURSEBASEISNULL);
        }
        CourseBase data = optional.get();
        data.setName(courseBase.getName());
        data.setUsers(courseBase.getUsers());
        data.setMt(courseBase.getMt());
        data.setSt(courseBase.getSt());
        data.setGrade(courseBase.getGrade());
        data.setStudymodel(courseBase.getStudymodel());
        data.setDescription(courseBase.getDescription());
        CourseBase save = courseBaseRepository.save(data);
        if (save != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }


//    public ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket) {
//        if (StringUtils.isEmpty(courseId)) {
//            //返回异常，提示课程id为空
//            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
//        }
//        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
//        if (!optional.isPresent()) {
//            //返回异常，提示查询课程信息为空
//            ExceptionCast.cast(CourseCode.COURSE_SELECT_COURSEBASEISNULL);
//            return new ResponseResult(CommonCode.FAIL);
//        }
//        //JPA更新方法 save方法即为更新方法
//        CourseMarket update = courseMarketRepository.save(courseMarket);
//        if (update != null) {
//            return new ResponseResult(CommonCode.SUCCESS);
//        }
//        return new ResponseResult(CommonCode.FAIL);
//    }

    @Transactional()
    public ResponseResult addCoursePic(String courseId, String pic) {
        CoursePic coursePic = new CoursePic();
        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);
        //使用save()方法进行添加时，会根据主键courseId查找，若查找不为空，则进行更新操作；
        //若查找courseId为空，则新添加一条记录
        CoursePic save = coursePicRepository.save(coursePic);
        if (save != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    
    public CoursePic findCoursePic(String courseId) {
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        if (!optional.isPresent()) {
            //返回异常，提示查询的课程图片信息为空
            ExceptionCast.cast(CourseCode.COURSE_SELECT_COURSEPICISNULL);
        }
        return optional.get();
    }

    @Transactional
    
    public ResponseResult deleteCoursePic(String courseId) {
        //删除课程图片信息，删除成功返回1，失败返回0
        long delete = coursePicRepository.deleteByCourseid(courseId);
        if (delete > 0) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    
    public CourseView getCourseView(String id) {
        CourseView courseView = new CourseView();
        //课程基本信息
        Optional<CourseBase> optionalCourseBase = courseBaseRepository.findById(id);
        if (optionalCourseBase.isPresent()) {
            courseView.setCourseBase(optionalCourseBase.get());
        }
        //课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(id);
        if (courseMarketOptional.isPresent()) {
            courseView.setCourseMarket(courseMarketOptional.get());
        }
        //课程图片信息
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(id);
        if (coursePicOptional.isPresent()) {
            courseView.setCoursePic(coursePicOptional.get());
        }
        //课程教学计划
        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }

    
    public CoursePublishResult previewCourse(String id) {
        //根据课程Id查询课程信息
        Optional<CourseBase> optional = courseBaseRepository.findById(id);
        if (!optional.isPresent()) {
            //课程信息不存在，返回异常
            ExceptionCast.cast(CourseCode.COURSE_SELECT_COURSEBASEISNULL);
        }
        //根据配置数据设置页面数据
        CmsPage cmsPage = new CmsPage();
        //拼接页面数据url
        cmsPage.setDataUrl(publish_dataUrlPre + id);
        //设置页面名称为课程id.html
        cmsPage.setPageName(id + ".html");
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        cmsPage.setPageWebPath(publish_page_webpath);
        cmsPage.setTemplateId(publish_templateId);
        cmsPage.setSiteId(publish_siteId);
        //设置页面别名为课程名称
        cmsPage.setPageAliase(optional.get().getName());
        //设置页面信息，调用cms远程接口保存页面信息，返回pageId
        CmsPageResult cmsPageResult = cmsPageClient.saveCmsPage(cmsPage);
        if (!CmsPageResult.SUCCESS) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_SAVEPAGEERROR);
            return new CoursePublishResult(CommonCode.FAIL, "保存页面失败");
        }
        //返回预览url，预览url包含:previewUrl+pageId
        String url = previewUrl + cmsPageResult.getCmsPage().getPageId();
        return new CoursePublishResult(CommonCode.SUCCESS, url);
    }

    @Transactional
    
    public CoursePublishResult publish(String id) {
        //根据课程id获取课程信息
        CourseBase courseBase = this.findCourseById(id);
        //根据课程信息拼接cmsPage
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);
        cmsPage.setPageAliase(courseBase.getName());
        cmsPage.setDataUrl(publish_dataUrlPre + id);
        cmsPage.setTemplateId(publish_templateId);
        cmsPage.setPageWebPath(publish_page_webpath);
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        cmsPage.setPageName(courseBase.getId() + ".html");
        cmsPage.setPageCreateTime(new Date());
        cmsPage.setPageType("0");
        //远程调用cms一键发布接口，返回页面url
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);
        if (!cmsPostPageResult.isSuccess()) {
            //发布页面失败
            return new CoursePublishResult(CommonCode.FAIL, null);
        }
        //创建课程索引对象
        CoursePub coursePub = createCoursePub(id);
        //设置课程缓存,向数据库保存课程索引对象
        CoursePub saveCoursePub = saveCoursePub(id, coursePub);
        if (saveCoursePub == null) {
            //提示创建课程索引信息失败
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_CREATE_INDEX_ERROR);
        }
        //更改课程状态为已发布
        updateStatus(courseBase.getId());
        //保存课程计划媒资信息到课程计划媒资信息发布表里
        saveTeachPlanMediaPub(courseBase.getId());
        //返回发布后的页面url
        String pageUrl = cmsPostPageResult.getPageUrl();
        return new CoursePublishResult(CommonCode.SUCCESS, pageUrl);
    }

    
    public ResponseResult saveMedia(TeachplanMedia teachplanMedia) {
        //判断添加信息是否为空
        if (teachplanMedia == null || StringUtils.isEmpty(teachplanMedia.getCourseId())
                || StringUtils.isEmpty(teachplanMedia.getMediaId())
                || StringUtils.isEmpty(teachplanMedia.getTeachplanId())) {
            //提示主要参数为空
            ExceptionCast.cast(CourseCode.SAVE_MEDIA_PARAM_IS_NULL);
        }
        String teachplanId = teachplanMedia.getTeachplanId();
        Teachplan teachPlan = this.getTeachPlan(teachplanId);
        //获取课程计划节点级别
        String grade = teachPlan.getGrade();
        //只允许课程计划的叶子节点选择视频
        if (StringUtils.isEmpty(grade) || !"3".equals(grade)) {
            //提示绑定视频的课程计划不是叶子节点
            return new ResponseResult(false, 11111, "只有章节下的课程计划才可以选择视频");
        }
        Optional<TeachplanMedia> optional = teachplanMediaRepository.findById(teachplanId);
        TeachplanMedia saveData = null;
        if (optional.isPresent()) {
            saveData = optional.get();
        } else {
            saveData = new TeachplanMedia();
        }
        //保存课程计划与媒资信息
        saveData.setCourseId(teachplanMedia.getCourseId());
        saveData.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
        saveData.setMediaId(teachplanMedia.getMediaId());
        saveData.setMediaUrl(teachplanMedia.getMediaUrl());
        saveData.setTeachplanId(teachplanId);
        TeachplanMedia save = teachplanMediaRepository.save(saveData);
        if (save != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //*********************************************私有方法************************************************

    /**
     * 根据课程id获取根节点,即课程名称的id
     * 若没有根节点，则根据课程id和课程名创建一个根节点,添加到数据库
     *
     * @param courseId
     * @return
     */
    private String getParentId(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()) {
            //查询课程信息为空
            ExceptionCast.cast(CourseCode.COURSE_SELECT_COURSEBASEISNULL);
        }
        //获取课程信息
        CourseBase courseBase = optional.get();
        //根据课程id和父节点为0查找课程计划根节点（名称为课程名称的根节点）；若根节点不存在，则在数据库新增
        List<Teachplan> list = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if (list == null || list.size() == 0) {
            //根节点不存在，创建该课程计划的根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setCourseid(courseId);
            //级别:1
            teachplan.setGrade("1");
            //根节点父节点都是0
            teachplan.setParentid("0");
            teachplan.setPname(courseBase.getName());
            //未发布:0
            teachplan.setStatus("0");
            Teachplan data = teachplanRepository.save(teachplan);
            //返回根节点id
            return data.getId();
        }
        //根节点存在，返回根节点id
        Teachplan teachplan = list.get(0);
        return teachplan.getId();
    }

    /**
     * 更新课程状态为202002:已发布
     *
     * @param courseId
     * @return
     */
    private CourseBase updateStatus(String courseId) {
        CourseBase courseBase = this.findCourseById(courseId);
        courseBase.setStatus("202002");
        CourseBase save = courseBaseRepository.save(courseBase);
        return save;
    }

    /**
     * 创建课程发布索引对象
     *
     * @param courseId
     * @return
     */
    public CoursePub createCoursePub(String courseId) {
        CoursePub coursePub = new CoursePub();
        coursePub.setId(courseId);
        //保存课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if (courseBaseOptional.isPresent()) {
            CourseBase courseBase = courseBaseOptional.get();
            BeanUtils.copyProperties(courseBase, coursePub);
        }
        //保存课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(courseId);
        if (courseMarketOptional.isPresent()) {
            CourseMarket courseMarket = courseMarketOptional.get();
            BeanUtils.copyProperties(courseMarket, coursePub);
        }
        //保存课程图片信息
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(courseId);
        if (coursePicOptional.isPresent()) {
            CoursePic coursePic = coursePicOptional.get();
            BeanUtils.copyProperties(coursePic, coursePub);
        }
        //将课程计划信息转换为字符串类型
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        String teachPlan = JSON.toJSONString(teachplanNode);
        coursePub.setTeachplan(teachPlan);
        return coursePub;
    }

    /**
     * 保存课程索引对象到数据库
     * 保存课程发布信息到课程发布表
     *
     * @return
     */
    public CoursePub saveCoursePub(String courseId, CoursePub coursePub) {
        if (StringUtils.isEmpty(courseId)) {
            //提示课程id为空
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        CoursePub coursePubNew = null;
        Optional<CoursePub> coursePubOptional = coursePubRepository.findById(courseId);
        if (coursePubOptional.isPresent()) {
            coursePubNew = coursePubOptional.get();
        } else {
            coursePubNew = new CoursePub();
        }
        BeanUtils.copyProperties(coursePub, coursePubNew);
        coursePubNew.setId(courseId);
        //设置时间戳
        coursePubNew.setTimestamp(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式化发布时间，转换为字符串
        String date = simpleDateFormat.format(new Date());
        coursePubNew.setPubTime(date);
        CoursePub coursePubSave = coursePubRepository.save(coursePubNew);
        return coursePubSave;
    }

    /**
     * 保存课程计划媒资发布信息到课程计划媒资发布表
     *
     * @param courseId
     */
    private void saveTeachPlanMediaPub(String courseId) {
        //根据courseId删除课程计划媒资发布表数据
        teachplanMediaPubRepository.deleteByCourseId(courseId);
        //根据courseId查询课程计划媒资表数据
        List<TeachplanMedia> teachplanMediaList = teachplanMediaRepository.findByCourseId(courseId);
        //将课程媒资信息复制到课程媒资发布信息集合里
        List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<>();
        for (TeachplanMedia teachplanMedia : teachplanMediaList) {
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            //两个集合的实体类不相同，但是实体类字段除时间戳外都相同，可直接用复制方法
            BeanUtils.copyProperties(teachplanMedia, teachplanMediaPub);
            teachplanMediaPubList.add(teachplanMediaPub);
        }
        //保存数据到课程计划媒资发布表
        teachplanMediaPubRepository.saveAll(teachplanMediaPubList);
    }

    
    public CoursePub getCoursePub(String courseId) {
        Optional<CoursePub> optional = coursePubRepository.findById(courseId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    
    public List<CoursePub> recommendCourse(String idsString) {
        List<CoursePub> recommendList = new ArrayList<>();
        //获取课程id
        List<String> ids = (List<String>) JSON.parse(idsString);
        //用户没有收藏课程和学习过的课程，随机推荐
        if (ids.size() == 0 || ids == null) {
            //随机推荐
            recommendList = coursePubRepository.findByGradeOrderByPubTime("200002");
            if (recommendList.size() > 6) {
                recommendList = recommendList.subList(0, 6);
            }
        } else {
            //根据用户收藏或者用户学习推荐
            for (String id : ids) {
                CoursePub coursePub = this.getCoursePub(id);
                //获取课程级别
                String grade = coursePub.getGrade();
                //获取大分类
                String mt = coursePub.getMt();
                //获取小分类
                String st = coursePub.getSt();
                //根据分类信息和课程级别查询课程
                List<CoursePub> dataList = getCourseByData(grade, mt, st);
                recommendList.addAll(dataList);
            }
            //推荐课程最多六门课程
            if (recommendList.size() > 6) {
                List<CoursePub> coursePubs = recommendList.subList(0, 6);
                return coursePubs;
            }
        }
        return recommendList;
    }

    /**
     * 根据课程分类和课程等级查询符合的课程信息
     *
     * @param grade
     * @param mt
     * @param st
     * @return
     */
    private List<CoursePub> getCourseByData(String grade, String mt, String st) {
        //全都匹配
        List<CoursePub> listAll = coursePubRepository.findByGradeAndMtAndSt(grade, mt, st);
        if (listAll.size() > 0) {
            //同种类型只推荐两门课程
            if (listAll.size() > 2) {
                return listAll.subList(0, 2);
            }
            return listAll;
        }
        //符合分类信息
        List<CoursePub> listMt = coursePubRepository.findByMtAndSt(mt, st);
        if (listMt.size() > 0) {
            //分类信息匹配推荐一门
            return (List<CoursePub>) listMt.get(0);
        }
        //符合部分分类信息
        List<CoursePub> byGradeAndmt = coursePubRepository.findByGradeAndMt(grade, mt);
        if (byGradeAndmt.size() > 0) {
            //部分匹配推荐一门课程
            return (List<CoursePub>) byGradeAndmt.get(0);
        }
        return null;
    }

}
