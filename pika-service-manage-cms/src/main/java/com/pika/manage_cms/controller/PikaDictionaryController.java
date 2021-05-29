package com.pika.manage_cms.controller;

import com.pika.api.course.PikaDictionaryControllerApi;

import com.pika.framework.domain.system.PikaDictionary;
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
public class PikaDictionaryController implements PikaDictionaryControllerApi {
    @Autowired
    DictionaryService dictionaryService;

    @Override
    @GetMapping("/dictionary/get/{dType}")
    public PikaDictionary getByType(@PathVariable("dType") String dType) {
        return dictionaryService.getByType(dType);
    }
}
