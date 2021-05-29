package com.pika.manage_course.dao;

import com.pika.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryMapper {

    /**
     * 获取分类信息列表
     * @return
     */
    CategoryNode findCategoryList();

}
