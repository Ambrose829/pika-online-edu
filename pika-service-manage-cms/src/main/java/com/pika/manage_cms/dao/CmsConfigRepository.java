package com.pika.manage_cms.dao;

import com.pika.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Pika
 * @create 2020/11/2
 * @description
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig, String> {

}
