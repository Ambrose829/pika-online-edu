package com.pika.framework.domain.course.request;

import com.pika.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@Data
@ToString
public class CourseListRequest extends RequestData {
    //公司Id
    private String companyId;
}
