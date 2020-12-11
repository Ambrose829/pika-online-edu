package com.pika.framework.domain.cms;

import lombok.Data;
import lombok.ToString;

/**
 * @author Pika
 * @create 2020/10/29
 * @discription 页面参数
 */
@Data
@ToString
public class CmsPageParam {
   //参数名称
    private String pageParamName;
    //参数值
    private String pageParamValue;

}
