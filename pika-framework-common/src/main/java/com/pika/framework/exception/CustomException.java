package com.pika.framework.exception;

import com.pika.framework.model.response.ResultCode;

/**
 * @author Pika
 * @create 2020/11/2
 * @description 自定义异常类型
 */
public class CustomException extends RuntimeException {
    //错误代码
    ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
