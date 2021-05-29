package com.pika.framework.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


/**
 * @author Pika
 * @create 2020/10/29
 * @discription cms页面模板
 *
 */
@Data
@ToString
@Document(collection = "cms_template")
public class CmsTemplate {

    //站点ID
    private String siteId;
    //模版ID
    @Id
    private String templateId;
    //模版名称
    private String templateName;
    //模版参数
    private String templateParameter;
    //模板文件路径
    private String templateFilePath;
    //模版文件Id
    private String templateFileId;

    private Date templateCreateTime;
}
