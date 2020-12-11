package com.pika.manage_cms.dao;

import com.pika.framework.domain.cms.CmsPage;
import com.pika.framework.domain.cms.CmsPageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Pika
 * @create 2020/10/29
 * @description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    CmsPageRepository cmsPageRepository;
    //查找全部
    @Test
    public void testFindAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }
    //分页查找
    @Test
    public void testFindPage() {
        //分页参数
        int page = 1; //从0开始
        int size = 10; //取10个
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);

    }
    @Test
    public void testFindAllByExample() {
        //分页参数
        int page = 0;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);

        //条件值对象
        CmsPage cmsPage= new CmsPage();
        //要查询5a751fab6abb5044e0d19ea1站点的页面
//        cmsPage.setSiteId("5b30b052f58b4411fc6cb1cf");
        //设置模板id条件
//        cmsPage.setTemplateId("5ad9a24d68db5239b8fef199");
        //设置页面别名
        cmsPage.setPageAliase("轮播");
        //条件匹配器
//        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
//        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //ExampleMatcher.GenericPropertyMatchers.contains() 包含关键字
//        ExampleMatcher.GenericPropertyMatchers.startsWith()//前缀匹配
        //定义Example
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }

    //增加
    @Test
    public void testInsert() {
        //定义实体类
         CmsPage cmsPage = new CmsPage();
         cmsPage.setSiteId("s01");
         cmsPage.setTemplateId("t01");
         cmsPage.setPageName("测试页面");
         cmsPage.setPageCreateTime(new Date());
         List<CmsPageParam> cmsPageParams = new ArrayList<>();
         CmsPageParam cmsPageParam = new CmsPageParam();
         cmsPageParam.setPageParamName("param1");
         cmsPageParam.setPageParamValue("value1");
         cmsPageParams.add(cmsPageParam);
         cmsPage.setPageParams(cmsPageParams);
         cmsPageRepository.save(cmsPage);
         System.out.println(cmsPage);

    }

    //删除
     @Test
     public void testDelete() {
        cmsPageRepository.deleteById("5f9b7d7480c7ce70f6ce21de");
    }

    //修改
    @Test
    public void testUpdate() {
        //查询对象
        Optional<CmsPage> optional = cmsPageRepository.findById("5f9b80713f537127955e8209");
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            //设置要修改的值
            cmsPage.setPageAliase("testP");
            //修改
            cmsPageRepository.save(cmsPage);
        }
    }
    //自定义查询
    @Test
    public void testFindByPageName() {
        CmsPage cmsPage = cmsPageRepository.findByPageName("测试页面");
        System.out.println(cmsPage);
    }


}
