package com.pika.manage_cms_client.dao;

import com.pika.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Pika
 * @create 2020/11/5
 * @description
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
}
