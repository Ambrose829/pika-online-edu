package com.pika.framework.domain.system;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@Data
@ToString
@Document(collection = "sys_dictionary")
public class PikaDictionary {

    @Id
    private String id;

    @Field("d_name")
    private String dName;

    @Field("d_type")
    private String dType;

    @Field("d_value")
    private List<PikaDictionaryValue> dValue;
}
