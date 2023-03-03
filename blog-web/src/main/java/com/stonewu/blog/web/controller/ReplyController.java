package com.stonewu.blog.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.custom.ArticleWithMoreInfo;
import com.stonewu.blog.core.entity.custom.ReplyTree;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.ArticleService;
import com.stonewu.blog.core.service.MenberService;
import com.stonewu.blog.core.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/reply")
public class ReplyController extends AbstractController {

    @Resource
    private ReplyService replyService;
    @Resource
    private ArticleService articleService;
    @Resource
    private MenberService menberService;

    @RequestMapping(value = {"/{artCode}", "/{artCode}/page/{pageNum}"})
    @ResponseBody
    public ObjectResult replylist(HttpSession session, Model model, @PathVariable(name = "artCode") String artCode, @PathVariable(name = "pageNum", required = false) Integer pageNum) {
        Menber menberInfo = (Menber) session.getAttribute("menberInfo");
        ArticleWithMoreInfo article = articleService.selectArticleByIdentName(artCode, menberInfo);
        if (article == null || article.getId() == null) {
            return new ObjectResult(ApiResultType.NO_FOUND, "被评论的文章不存在");
        }
        Page<ReplyTree> page = new Page<>(1, 10);
        if (pageNum != null) {
            page.setCurrent(pageNum);
        }
        ReplyTree param = new ReplyTree();
        param.setArticleId(article.getId());
        param.setCheckReply(1);
        if (menberInfo != null) {
            param.setAuthorId(menberInfo.getId());
        }
        Page<ReplyTree> replyTrees = replyService.findReplyTreeByParam(page, param);
        model.addAttribute("replys", replyTrees);
        return new ObjectResult(ApiResultType.SUCCESS, replyTrees);
    }

}

