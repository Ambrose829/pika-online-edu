package com.pika.manage_course.service;

import com.pika.framework.domain.course.Category;
import com.pika.framework.domain.course.ext.CategoryNode;
import com.pika.manage_course.dao.CategoryMapper;
import com.pika.manage_course.dao.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 分类管理
 * @author yjw
 * @date 2019/11/12
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 获取分类信息列表
     * @return
     */
    public CategoryNode findCategoryList() {
        return categoryMapper.findCategoryList();
    }

    public Category getCategory(String id){
        Optional<Category> optional = categoryRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }


}
