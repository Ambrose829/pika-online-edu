package com.pika.manage_cms.dao;

import com.pika.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Pika
 * @create 2020/11/3
 * @description 模板类的dao
 */
public interface CmsTemplateRespository extends MongoRepository<CmsTemplate, String> {

}
