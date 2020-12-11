package com.pika.manage_cms.controller;

import com.pika.api.course.SysDicthinaryControllerApi;

import com.pika.framework.domain.system.SysDictionary;
import com.pika.manage_cms.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author Pika
 * @create 2020/11/9
 * @description
 */
@RestController
@RequestMapping("/sys")
public class SysDicthinaryController implements SysDicthinaryControllerApi {
    @Autowired
    DictionaryService dictionaryService;

    @Override
    @GetMapping("/dictionary/get/{dType}")
    public SysDictionary getByType(@PathVariable("dType") String dType) {
        return dictionaryService.getByType(dType);
    }
}
