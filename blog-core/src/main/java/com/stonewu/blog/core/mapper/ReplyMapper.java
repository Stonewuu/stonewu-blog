package com.stonewu.blog.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.entity.Reply;
import com.stonewu.blog.core.entity.custom.ReplyInfo;
import com.stonewu.blog.core.entity.custom.ReplyTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
public interface ReplyMapper extends BaseMapper<Reply> {

    /**
     * 按分页和条件查询回复列表
     *
     * @param page
     * @param param
     * @return
     */
    public List<ReplyInfo> findReplyInfoByPage(Page<ReplyInfo> page, @Param("param") ReplyInfo param);

    List<ReplyTree> findReplyTreeByParam(Page<ReplyTree> page, @Param("param") ReplyTree param);

    Page<ReplyTree> findReplyResultByParam(Page<ReplyTree> page, @Param("param") ReplyTree param);
}
