package com.pika.api.search;

import com.pika.framework.domain.course.CoursePub;
import com.pika.framework.domain.course.TeachplanMediaPub;
import com.pika.framework.domain.search.CourseSearchParam;
import com.pika.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * Created by Administrator.
 */
@Api(value = "课程搜索",description = "课程搜索",tags = {"课程搜索"})
public interface EsCourseControllerApi {
    //搜索课程信息
    @ApiOperation("课程综合搜索")
    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam);

    @ApiOperation("根据课程id查询课程信息")
    public Map<String,CoursePub> getall(String id);

    @ApiOperation("根据课程计划id查询课程媒资信息")
    public TeachplanMediaPub getmedia(String id);


}
