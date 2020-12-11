package com.pika.api.cms;

import com.pika.framework.domain.cms.CmsPage;
import com.pika.framework.domain.cms.request.QueryPageRequest;
import com.pika.framework.domain.cms.response.CmsPageResult;
import com.pika.framework.model.response.QueryResponseResult;
import com.pika.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author Pika
 * @create 2020/10/29
 * @since 1.0.0
 */
@Api(value = "cms页面管理系统", tags = {"cms页面管理接口，提供页面的增、删、改、查"})
public interface CmsPageControllerApi {
    //页面查询

    /**
     *
     * @param page 当前页数
     * @param size 每页显示的数据
     * @param queryPageRequest
     * @return
     */
    @ApiOperation("分页查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation("新增页面")
    CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("根据页面id查询页面信息")
    CmsPage findById(String id);

    @ApiOperation("修改页面")
    CmsPageResult edit(String id, CmsPage cmsPage);

    @ApiOperation("根据id删除页面")
    ResponseResult delete(String id);

    @ApiOperation("页面发布")
    public ResponseResult post(String pageId);



}
