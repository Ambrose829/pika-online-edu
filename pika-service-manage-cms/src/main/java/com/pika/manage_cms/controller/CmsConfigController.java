package com.pika.manage_cms.controller;

import com.pika.api.cms.CmsConfigControllerApi;
import com.pika.framework.domain.cms.CmsConfig;
import com.pika.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pika
 * @create 2020/11/2
 * @description
 */
@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    PageService pageService;
    @Override
    @GetMapping("/getmodel/{id}")
    public CmsConfig getModel(@PathVariable("id") String id) {
        return pageService.getConfigById(id);
    }
}
