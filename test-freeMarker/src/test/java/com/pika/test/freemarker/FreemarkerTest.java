package com.pika.test.freemarker;

import cn.hutool.core.io.BOMInputStream;
import cn.hutool.core.io.FileUtil;
import com.pika.test.freemarker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;


/**
 * @author Pika
 * @create 2020/11/2
 * @description freemarker测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkerTest {

    //测试静态化，基于ftlh生成html文件
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {
        //定义配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //定义模板
        //得到classPath的路径
        String classPath = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(classPath + "/templates/"));
        //获取模板文件的内容
        Template template = configuration.getTemplate("test1.ftlh");
        //定义数据类型
        Map map = getMap();
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        //静态化内容
//        System.out.println(content);
        InputStream inputStream = IOUtils.toInputStream(content, Charset.forName("utf-8"));
        //输出文件
        FileOutputStream fileOutputStream = new FileOutputStream(new File(classPath + "test1.html"));
        int copy = IOUtils.copy(inputStream, fileOutputStream);
        inputStream.close();
        fileOutputStream.close();


    }

    //基于模板文件的内容（字符串）生成html文件
    @Test
    public void testGenerateHtmlByString() throws IOException, TemplateException {
        //定义配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板内容，这里测试时使用简单的字符串作为模板
        String templateString="" +
                "<html>\n" +
                "    <head></head>\n" +
                "    <body>\n" +
                "    名称：${name}\n" +
                "    </body>\n" +
                "</html>";
        //使用一个模板加载器变为模板
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("test02", templateString);
        //在配置类中设置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        //获取模板的内容
        Template test02 = configuration.getTemplate("test02", "utf-8");



        //定义数据类型
        Map map = getMap();
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(test02, map);
        //静态化内容
//        System.out.println(content);
        InputStream inputStream = IOUtils.toInputStream(content, Charset.forName("utf-8"));
        //输出文件
        //得到classPath的路径
        String classPath = this.getClass().getResource("/").getPath();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(classPath + "test02.html"));
        int copy = IOUtils.copy(inputStream, fileOutputStream);
        inputStream.close();
        fileOutputStream.close();
    }

    @Test
    public void test() {
        String classPath = this.getClass().getResource("/").getPath();
        System.out.println(classPath);
    }


    //获取数据模型
    public Map getMap() {
        Map<String, Object> map = new HashMap<>();
        //向数据模型放数据
        map.put("name","黑马程序员");
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);
//        stu2.setBirthday(new Date());
        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
        stu2.setFriends(friends);
        stu2.setBestFriend(stu1);

        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        //向数据模型放数据
        map.put("stus",stus);
        //准备map数据
        HashMap<String,Student> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);
        //向数据模型放数据
        map.put("stu1",stu1);
        //向数据模型放数据
        map.put("stuMap",stuMap);
        return map;
    }
}
