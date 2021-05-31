package com.pika.api.ucenter;

import com.pika.framework.domain.ucenter.ext.PikaUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Administrator.
 */
@Api(value = "用户中心",description = "用户中心管理")
public interface UcenterControllerApi {
    @ApiOperation("根据用户账号查询用户信息")
    public PikaUserExt getUserext(String username);
}
