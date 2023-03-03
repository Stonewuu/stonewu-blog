package com.stonewu.blog.core.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.Reply;
import com.stonewu.blog.core.entity.custom.ReplyInfo;
import com.stonewu.blog.core.entity.custom.ReplyTree;
import com.stonewu.blog.core.mapper.ReplyMapper;
import com.stonewu.blog.core.service.BaseServiceImpl;
import com.stonewu.blog.core.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
@Service
public class ReplyServiceImpl extends BaseServiceImpl<ReplyMapper, Reply> implements ReplyService {
    private static final Logger log = LoggerFactory.getLogger(ReplyServiceImpl.class);

    @Resource
    private ReplyMapper mapper;

    @Override
    public Page<ReplyInfo> findReplyInfoByPage(Page<ReplyInfo> page, ReplyInfo param) {
        return page.setRecords(mapper.findReplyInfoByPage(page, param));
    }

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'findReplyTreeByParam_'+#param.articleId+'_'+#param.checkReply+'_'+#param.authorId+'_page_'+#page.current+'_'+#page.size")
    public Page<ReplyTree> findReplyTreeByParam(Page<ReplyTree> page, ReplyTree param) {
        Page<ReplyTree> result = mapper.findReplyResultByParam(page, param);
        getChildReply(new Page<>(1, 9999), param, result.getRecords());
        return result;
    }

    private void getChildReply(Page<ReplyTree> page, ReplyTree param, List<ReplyTree> result) {
        result.forEach(replyTree -> {
            Integer id = replyTree.getId();
            param.setParentReplyId(id);
            Page<ReplyTree> childReply = mapper.findReplyResultByParam(page, param);
            replyTree.setChildren(childReply.getRecords());
            getChildReply(page, param, childReply.getRecords());
        });
    }
}
