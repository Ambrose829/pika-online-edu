package com.pika.framework.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * @author Pika
 * @create 2020/10/29
 */
@Data
@ToString
public class QueryResponseResult<T> extends ResponseResult{
    QueryResult<T> queryResult;

    public QueryResponseResult(QueryResult queryResult) {
        this.queryResult = queryResult;
    }

    public QueryResponseResult(ResultCode resultCode, QueryResult<T> queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }
}
