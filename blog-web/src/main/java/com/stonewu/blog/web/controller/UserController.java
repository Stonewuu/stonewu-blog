package com.stonewu.blog.web.controller;


import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.Reply;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.ArticleService;
import com.stonewu.blog.core.service.MailService;
import com.stonewu.blog.core.service.ReplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stonewu
 * @since 2018-08-24
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Resource
    private ReplyService replyService;
    @Resource
    private ArticleService articleService;
    @Resource
    private MailService mailService;

    @RequestMapping(value = {"/doReply"}, method = RequestMethod.POST)
    @ResponseBody
    @CacheEvict(value = "Article", allEntries = true)
    public ObjectResult doReply(Model model, HttpSession session, Reply reply, String captcha, String sendmail) {
        if (StringUtils.isBlank(reply.getContent()) || reply.getArticleId() == null) {
            return new ObjectResult(ApiResultType.PARAM_ERROR);
        }
        if (reply.getContent().length() > 1024) {
            reply.setContent(reply.getContent().substring(0, 1024));
        }
        Menber menberInfo = (Menber) session.getAttribute("menberInfo");
        reply.setAuthorId(menberInfo.getId());
        reply.setCheckReply(0);
        boolean isSendMail = "y".equals(sendmail);
        reply.setSendEmail(isSendMail);
        reply.setReplyType(1);
        replyService.save(reply);
        /*if(isSendMail){
            // 发送邮件在审核评论那边做，这边不做
            mailService.sendMail(reply);
        }*/
        mailService.sendMailToAdmin(reply);
        return new ObjectResult(ApiResultType.SUCCESS, null);
    }
}

