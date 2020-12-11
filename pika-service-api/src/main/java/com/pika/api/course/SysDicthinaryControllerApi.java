package com.pika.api.course;

import com.pika.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Pika
 * @create 2020/11/9
 * @description
 */
@Api(value = "数据字典接口",tags = {"提供数据字典接口的管理、查询功能"})
public interface SysDicthinaryControllerApi {
    //数据字典
    @ApiOperation(value="数据字典查询接口")
    SysDictionary getByType(String type);

}
