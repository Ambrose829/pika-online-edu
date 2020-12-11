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
public class DeleteCourseResult extends ResponseResult {
    public DeleteCourseResult(ResultCode resultCode, String courseId) {
        super(resultCode);
        this.courseid = courseid;
    }
    private String courseid;

}
