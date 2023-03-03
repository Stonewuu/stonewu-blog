package com.stonewu.blog.core.service.impl;

import com.stonewu.blog.core.entity.Article;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.Reply;
import com.stonewu.blog.core.properties.MailProperties;
import com.stonewu.blog.core.service.ArticleService;
import com.stonewu.blog.core.service.MailService;
import com.stonewu.blog.core.service.MenberService;
import com.stonewu.blog.core.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    @Resource
    private MailProperties mailProperties;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private MenberService menberService;
    @Resource
    private ReplyService replyService;
    @Resource
    private ArticleService articleService;

    @Resource
    private TemplateEngine templateEngine;

    @Async
    @Override
    public void sendMail(Reply reply) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(mailProperties.getFrom());
            if (reply.getParentReplyId() == null) {
                return;
            }
            Reply parentReply = replyService.getById(reply.getParentReplyId());
            if (!parentReply.getSendEmail()) {
                return;
            }
            Menber menber = menberService.getById(parentReply.getAuthorId());
            Menber own = menberService.getById(reply.getAuthorId());
            Article article = articleService.getById(reply.getArticleId());

            Context context = new Context();
            context.setVariable("title", "有人回复了您的评论");
            context.setVariable("name", own.getNickName());
            context.setVariable("articleTitle", article.getTitle());
            context.setVariable("artcode", article.getIdentName());
            context.setVariable("content", reply.getContent());
            String content = templateEngine.process("mail", context);
            messageHelper.setText(content, true);
            messageHelper.setTo(menber.getEmail());
            messageHelper.setSubject("有人回复了您的评论");

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("发送HTML邮件时发生异常！", e);
        }
    }

    @Async
    @Override
    public void sendMailToAdmin(Reply reply) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(mailProperties.getFrom());
            messageHelper.setTo(mailProperties.getTo());


            Menber own = menberService.getById(reply.getAuthorId());
            Article article = articleService.getById(reply.getArticleId());

            Context context = new Context();
            context.setVariable("title", "有人回复了您的博文");
            context.setVariable("name", own.getNickName());
            context.setVariable("articleTitle", article.getTitle());
            context.setVariable("artcode", article.getIdentName());
            context.setVariable("content", reply.getContent());
            String content = templateEngine.process("mail", context);
            messageHelper.setText(content, true);
            messageHelper.setSubject("有人回复了您的博文");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("发送HTML邮件时发生异常！", e);
        }
    }
}
