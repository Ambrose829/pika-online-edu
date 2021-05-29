package com.pika.manage_course.service;


import com.pika.framework.domain.system.PikaDictionary;
import com.pika.manage_course.dao.PikaDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Pika
 * @description
 */
@Service
public class PikaDictionaryService {

    @Autowired
    private PikaDictionaryRepository pikaDictionaryRepository;

    /**
     * 根据数据字典类型获取分类数据
     *
     * @param type
     * @return
     */

    public PikaDictionary findBydType(String type) {
        return pikaDictionaryRepository.findBydType(type);
    }
}