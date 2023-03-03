package com.stonewu.blog.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stonewu.blog.core.entity.ArticleTagLink;
import com.stonewu.blog.core.entity.result.ObjectResult;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stonewu
 * @since 2018-09-05
 */
public interface ArticleTagLinkService extends IService<ArticleTagLink> {

    /**
     * 查询并插入标签关联
     *
     * @param articleId
     * @param tagId
     * @return
     */
    public ObjectResult findAndInsert(Integer articleId, Integer tagId);
}
