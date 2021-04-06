package com.pika.api.media;

import com.pika.framework.domain.media.MediaFile;
import com.pika.framework.domain.media.request.QueryMediaFileRequest;
import com.pika.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Administrator.
 */
@Api(value = "媒体文件管理",description = "媒体文件管理接口",tags = {"媒体文件管理接口"})
public interface MediaFileControllerApi {

    @ApiOperation("我的媒资文件查询列表")
    public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);

}

