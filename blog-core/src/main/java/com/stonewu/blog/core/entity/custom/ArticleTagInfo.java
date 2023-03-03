package com.stonewu.blog.core.entity.custom;

import com.stonewu.blog.core.entity.ArticleTag;

public class ArticleTagInfo extends ArticleTag {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer articleId;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

}
