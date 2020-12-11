package com.pika.manage_cms.dao;

import com.pika.manage_cms.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Pika
 * @create 2020/11/3
 * @description pageService的测试类
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageServiceTest {
    @Autowired
    PageService pageService;

    @Test
    public void testGetPageHtml() {
        String pageHtml = pageService.getPageHtml("5f9f704823948e7c84c79955");
        System.out.println(pageHtml);
    }
}
