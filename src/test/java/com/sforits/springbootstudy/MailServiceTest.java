package com.sforits.springbootstudy;

import com.sforits.springbootstudy.service.MailService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;

/**
 * @Author：sforits
 * @E-mail：sforits@gmail.com
 * @Date：2020/10/22-13:32 Created by IntelliJ IDEA.
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {
    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 测试简单邮件的发送
     */
    @Test
    public void testSimpleMail() {
        mailService.sendSimpleMail(
                "2253716793@qq.com",
                "test simple mail",
                "Hello, this is a simple mail!\nhttps://baidu.com");
    }

    @Test
    public void testHtmlMail() {
        String content = "<html><body><h3>这是一封html邮件</h3></body><html>";
        mailService.sendHtmlMail("2253716793@qq.com", "html邮件", content);
    }
}