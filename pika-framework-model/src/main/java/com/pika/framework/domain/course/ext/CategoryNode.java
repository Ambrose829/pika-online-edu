package com.pika.framework.domain.course.ext;

import com.pika.framework.domain.course.Category;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Locale;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@Data
@ToString
public class CategoryNode extends Category {
    List<CategoryNode> children;
}
