package com.stonewu.blog.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stonewu.blog.core.entity.ArticleTag;
import com.stonewu.blog.core.entity.custom.ArticleTagInfo;
import com.stonewu.blog.core.entity.custom.TagCountInfo;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stonewu
 * @since 2018-09-05
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    public List<ArticleTagInfo> findTagByArticleId(Integer id);

    public List<TagCountInfo> getMostArtTag();
}
