package com.pika.manage_course.controller;

import com.pika.api.course.PikaDictionaryControllerApi;
import com.pika.framework.domain.system.PikaDictionary;
import com.pika.manage_course.service.PikaDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据字典管理
 *
 * @author yjw
 * @date 2019/11/12
 */
@RestController
@RequestMapping("/course/sys/dictionary")
public class PikaDictionaryController implements PikaDictionaryControllerApi {

    @Autowired
    private PikaDictionaryService pikaDictionaryService;

    /**
     * 根据数据字典类型查询字典数据
     * @param type
     * @return
     */
    @GetMapping("/get/{dType}")
    @Override
    public PikaDictionary getByType(@PathVariable("dType") String type) {
        return pikaDictionaryService.findBydType(type);
    }


}
