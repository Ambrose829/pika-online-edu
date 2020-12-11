package com.pika.api.cms;

import com.pika.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;

/**
 * @author Pika
 * @create 2020/11/2
 * @description
 */
@Api(value = "cms页面配置管理接口", tags = {"获取静态页面"})
public interface CmsConfigControllerApi {

    CmsConfig getModel(String id);
}
