package com.pika.manage_cms.controller;

import com.pika.framework.web.BaseController;
import com.pika.manage_cms.service.PageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * @author Pika
 * @create 2020/11/3
 * @description
 */
@Controller
public class CmsPageServiceController extends BaseController {
    @Autowired
    PageService pageService;

    //接收页面Id
    @GetMapping("/cms/preview/{pageId}")
    public void preview(@PathVariable("pageId") String pageId) {
        //执行静态化
        String pageHtml = pageService.getPageHtml(pageId);
        if (StringUtils.isNotEmpty(pageHtml)) {
            try {
                //通过response对象将内容输出
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(pageHtml.getBytes("utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
