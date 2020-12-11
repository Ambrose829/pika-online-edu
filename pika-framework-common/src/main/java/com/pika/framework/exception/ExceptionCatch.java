package com.pika.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.pika.framework.model.response.CommonCode;
import com.pika.framework.model.response.ResponseResult;
import com.pika.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Pika
 * @create 2020/11/2
 * @description 统一的异常捕获类
 */

@ControllerAdvice//控制器增强
public class ExceptionCatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    //定义一个Map，配置异常类锁对应的项目代码
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    //定义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult customException(Exception exception) {
        //记录日志
        LOGGER.error("catch exception:{}", exception.getMessage());

        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();//EXCEPTIONS构建成功，不能更改
        }
        //从EXCEPTIONS中找到异常类型所对应的错误代码，如果找到了将错误代码相应给用户，如果找不到响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());

        if (resultCode != null) {
            return new ResponseResult(resultCode);
        } else {
            //返回99999异常
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }


    }

    static {
        //定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
    }
}
