package com.pika.api.course;

import com.pika.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "课程分类管理", tags = {"课程分类管理"})
public interface CategoryControllerApi {

    /**
     * 查询分类列表
     *
     * @return
     */
    @ApiOperation("查询分类列表")
    CategoryNode findCategoryList();

}
