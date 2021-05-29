package com.pika.framework.domain.system;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Pika
 * @create 2020/11/9
 * @description
 */
@Data
@ToString
public class PikaDictionaryValue {

    @Field("sd_id")
    private String sdId;

    @Field("sd_name")
    private String sdName;

    @Field("sd_status")
    private String sdStatus;
}
