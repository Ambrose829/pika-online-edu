package com.pika.framework.domain.cms;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author Pika
 * @create 2020/11/2
 * @description
 */
@Data
@ToString
public class CmsConfigModel {
    private String key;
    private String name;
    private String url;
    private Map mapValue;
    private String value;

}
