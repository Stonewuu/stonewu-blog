package com.stonewu.blog.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stonewu.blog.core.entity.Reply;
import com.stonewu.blog.core.entity.custom.ReplyInfo;
import com.stonewu.blog.core.entity.custom.ReplyTree;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
public interface ReplyService extends IService<Reply> {

    /**
     * 按分页和条件查询回复列表
     *
     * @param page
     * @param param
     * @return
     */
    Page<ReplyInfo> findReplyInfoByPage(Page<ReplyInfo> page, ReplyInfo param);

    Page<ReplyTree> findReplyTreeByParam(Page<ReplyTree> page, ReplyTree param);
}
