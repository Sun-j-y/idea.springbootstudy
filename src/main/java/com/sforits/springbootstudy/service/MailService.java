package com.sforits.springbootstudy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @Author：sforits
 * @E-mail：sforits@gmail.com
 * @Date：2020/10/16-14:11 Created by IntelliJ IDEA.
 */
@Component
public class MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * 发送简单邮件
     *
     * @param receiver 接收地址
     * @param subject  标题
     * @param content  内容
     */
    public void sendSimpleMail(String receiver, String subject, String content) {
        logger.info(sender);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(receiver);
        message.setText(content);
        message.setSubject(subject);

        try {
            mailSender.send(message);
            logger.info("简单邮件已发送");
        } catch (MailException e) {
            logger.error("发送简单邮件失败", e);
        }
    }

    /**
     * 发送富文本(html)邮件
     *
     * @param receiver 接收地址
     * @param subject  标题
     * @param content  内容
     */
    public void sendHtmlMail(String receiver, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件失败", e);
        }
    }

    /**
     * 发送带附件的邮件
     *
     * @param receiver 接收地址
     * @param subject  标题
     * @param content  内容
     * @param filePath
     */
    public void sendAttachmentsMail(String receiver, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            // true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }

    /**
     * 发送嵌入静态资源（一般是图片）的邮件
     *
     * @param receiver 接收地址
     * @param subject  标题
     * @param content  邮件内容，需要包括一个静态资源的id，比如：<img src=\"cid:rscId01\" >
     * @param rscPath  静态资源路径和文件名
     * @param rscId    静态资源id
     */
    public void sendInlineResourceMail(String receiver, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }

}