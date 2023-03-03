package com.stonewu.blog.core.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.entity.Article;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.custom.ArticleWithMoreInfo;
import com.stonewu.blog.core.entity.custom.YearGroup;
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
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 分页查询文章列表
     *
     * @param page
     * @return
     */
    public List<ArticleWithMoreInfo> selectArticleMoreWithReply(Page page, @Param(Constants.WRAPPER) Wrapper wrapper, @Param("menberParam") Menber param);

    /**
     * 查询单个博文，包含回复数量
     *
     * @param wrapper
     * @return
     */
    public ArticleWithMoreInfo selectArticleMoreWithReply(@Param(Constants.WRAPPER) Wrapper wrapper, @Param("menberParam") Menber param);

    public List<ArticleWithMoreInfo> selectArticleMore(Page page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 查询最近五条有回复的文章
     *
     * @param page
     * @param wrapper
     * @return
     */
    public List<ArticleWithMoreInfo> selectRecentReply(Page page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 按标签名查询文章
     *
     * @param page
     * @param tagName
     * @return
     */
    public List<ArticleWithMoreInfo> selectByTagNamePage(Page page, @Param("tagName") String tagName, @Param("menberParam") Menber menberInfo);

    public List<YearGroup> selectGroupByCreateTime();

}
