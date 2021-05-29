package com.pika.manage_course.dao;

import com.pika.framework.domain.system.PikaDictionary;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 分类管理
 * MongoDB数据库表sys_dictionary
 * @author  yjw
 * on 2019/11/12
 */
public interface PikaDictionaryRepository extends MongoRepository<PikaDictionary,String> {

    /**
     * 根据字典分类查询数据字典信息
     * @param dType
     * @return
     */
    PikaDictionary findBydType(String dType);

}
