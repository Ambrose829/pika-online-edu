package com.pika.manage_course.controller;

import com.pika.api.course.CategoryControllerApi;
import com.pika.framework.domain.course.Category;
import com.pika.framework.domain.course.ext.CategoryNode;
import com.pika.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/course/category")
public class CategoryController implements CategoryControllerApi {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @Override
    public CategoryNode findCategoryList() {
        return categoryService.findCategoryList();
    }

    @GetMapping("/get/{id}")
    public Category getCategoryById(@PathVariable("id")String id){
        return categoryService.getCategory(id);
    }

}
