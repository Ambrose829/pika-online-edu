package com.pika.framework.domain.cms.response;

import com.pika.framework.domain.cms.CmsPage;
import com.pika.framework.model.response.ResponseResult;
import com.pika.framework.model.response.ResultCode;
import lombok.Data;
/**
 * @author Pika
 * @create 2020/11/1
 * @description
 */

@Data
public class CmsPageResult extends ResponseResult {
    CmsPage cmsPage;
    public CmsPageResult(ResultCode resultCode, CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
