package com.stonewu.blog.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.entity.Reply;
import com.stonewu.blog.core.entity.custom.ReplyInfo;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.MailService;
import com.stonewu.blog.core.service.ReplyService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
@RestController
@RequestMapping("/admin/reply")
@CrossOrigin
public class AdminReplyController extends AbstractController {

    @Resource
    private ReplyService replyService;
    @Resource
    private MailService mailService;

    @RequestMapping("/list")
    public ObjectResult list(Model model, Page<ReplyInfo> page, ReplyInfo param) {
        QueryWrapper<ReplyInfo> wrapper = new QueryWrapper<ReplyInfo>();
        wrapper.setEntity(param);
        page = replyService.findReplyInfoByPage(page, param);
        return new ObjectResult(ApiResultType.SUCCESS, page);
    }

    @RequestMapping("/deleteReply/{id}")
    @CacheEvict(value = "Article", allEntries = true)
    public ObjectResult deleteReply(Model model, @PathVariable(name = "id", required = true) Integer id) {
        boolean isDelete = replyService.removeById(id);
        if (isDelete) {
            return new ObjectResult(ApiResultType.SUCCESS);
        }
        return new ObjectResult(ApiResultType.UNKNOW_ERROR, "删除评论失败！");
    }

    @RequestMapping("/changeStatus/{id}/status/{status}")
    @CacheEvict(value = "Article", allEntries = true)
    public ObjectResult changeStatus(Model model, @PathVariable(name = "id", required = true) Integer id,
                                     @PathVariable(name = "status", required = true) Integer status) {
        Reply param = new Reply();
        param.setId(id);
        param.setCheckReply(status);
        boolean isUpdate = replyService.updateById(param);
        if (isUpdate) {
            if(status == 1){
                Reply reply = replyService.getById(id);
                mailService.sendMail(reply);
            }
            return new ObjectResult(ApiResultType.SUCCESS);
        }
        return new ObjectResult(ApiResultType.UNKNOW_ERROR, "更新评论审核状态失败！");
    }

    @RequestMapping("/changeEmailStatus/{id}/status/{isSendMail}")
    public ObjectResult changeEmailStatus(Model model, @PathVariable(name = "id", required = true) Integer id,
                                          @PathVariable(name = "isSendMail", required = true) boolean isSendMail) {
        Reply param = new Reply();
        param.setId(id);
        param.setSendEmail(isSendMail);
        boolean isUpdate = replyService.updateById(param);
        if (isUpdate) {
            return new ObjectResult(ApiResultType.SUCCESS);
        }
        return new ObjectResult(ApiResultType.UNKNOW_ERROR, "更新邮件发送开关状态失败！");
    }
}
