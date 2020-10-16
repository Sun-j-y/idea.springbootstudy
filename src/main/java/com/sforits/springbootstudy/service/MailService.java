package com.sforits.springbootstudy.service;

import com.sforits.springbootstudy.entity.MailVo;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @Author：sforits
 * @E-mail：sforits@gmail.com
 * @Date：2020/10/16-14:11 Created by IntelliJ IDEA.
 */
@Service
public class MailService {
    @Autowired
    private JavaMailSenderImpl mailSender;


    public void checkMail(MailVo mailVo) {
        if (StringUtils.isEmpty(mailVo.getTo())) {
            throw new RuntimeException("邮件收信人不能为空");
        }
        if (StringUtils.isEmpty(mailVo.getSubject())) {
            throw new RuntimeException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(mailVo.getText())) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }

    public void sendMimeMail() {

    }

//    public void send


    public void sendMail() throws MessagingException, javax.mail.MessagingException {
        //简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("sforits@163.com");
        simpleMailMessage.setTo("forits@qq.com");
        simpleMailMessage.setSubject("Happy New Year");
        simpleMailMessage.setText("新年快乐！");
        mailSender.send(simpleMailMessage);

        //复杂邮件
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//        messageHelper.setFrom("admin@163.com");
//        messageHelper.setTo("socks@qq.com");
//        messageHelper.setSubject("Happy New Year");
//        messageHelper.setText("新年快乐！");
//        messageHelper.addInline("doge.gif", new File("xx/xx/doge.gif"));
//        messageHelper.addAttachment("work.docx", new File("xx/xx/work.docx"));
//        mailSender.send(mimeMessage);
    }

    @Test
    public void test() throws MessagingException, javax.mail.MessagingException {
        sendMail();
    }
}