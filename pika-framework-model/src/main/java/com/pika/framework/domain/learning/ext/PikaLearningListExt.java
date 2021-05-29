package com.pika.framework.domain.learning.ext;

import com.pika.framework.domain.learning.PikaLearningList;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class PikaLearningListExt extends PikaLearningList {

    private String courseName;

    private String pic;


}
