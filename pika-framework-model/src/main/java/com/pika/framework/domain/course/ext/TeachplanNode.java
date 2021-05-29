package com.pika.framework.domain.course.ext;

import com.pika.framework.domain.course.Teachplan;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@Data
@ToString
public class TeachplanNode extends Teachplan {
    /**
     * 课程计划子节点
     */
    List<TeachplanNode> children;

    /**
     * 媒资文件id
     */
    private String mediaId;

    /**
     * 媒资文件原始名
     */
    private String mediaFileOriginalName;

}
