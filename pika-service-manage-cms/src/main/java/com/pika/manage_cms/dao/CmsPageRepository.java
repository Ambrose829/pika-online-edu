package com.pika.manage_cms.dao;

import com.pika.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Pika
 * @create 2020/10/29
 * @description
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
    //根据页面名称查询
    CmsPage findByPageName(String pageName);
    //根据页面名称、站点ID、页面webpath来查询
    CmsPage findByPageNameAndAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}
