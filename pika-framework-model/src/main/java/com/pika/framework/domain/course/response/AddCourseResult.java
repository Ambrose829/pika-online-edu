package com.pika.framework.domain.course.response;

import com.pika.framework.model.response.ResponseResult;
import com.pika.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * @author Pika
 * @create 2020/11/9
 * @description
 */
@Data
@ToString
public class AddCourseResult extends ResponseResult {
    public AddCourseResult(ResultCode resultCode, String courseid) {
        super(resultCode);
        this.courseid = courseid;
    }

    private String courseid;

}