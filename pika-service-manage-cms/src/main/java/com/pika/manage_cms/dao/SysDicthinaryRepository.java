package com.pika.manage_cms.dao;

import com.pika.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * @author Pika
 * @create 2020/11/9
 * @description
 */
public interface SysDicthinaryRepository extends MongoRepository<SysDictionary, String> {

    SysDictionary findBydType(String dType);

}
