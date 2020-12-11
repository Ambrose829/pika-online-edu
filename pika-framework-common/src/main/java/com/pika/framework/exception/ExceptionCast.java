package com.pika.framework.exception;

import com.pika.framework.model.response.ResultCode;

/**
 * @author Pika
 * @create 2020/11/2
 * @description 异常抛出类
 */
public class ExceptionCast {
    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }
}
