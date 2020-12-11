package com.pika.framework.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Pika
 * @create 2020/10/29
 */
@Data
@ToString
public class QueryResult<T> {
    //数据列表
    private List<T> list;
    //数据总数
    private long total;
}
