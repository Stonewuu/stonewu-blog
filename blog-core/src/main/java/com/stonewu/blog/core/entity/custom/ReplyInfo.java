package com.stonewu.blog.core.entity.custom;

import com.stonewu.blog.core.entity.Reply;

public class ReplyInfo extends Reply {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String articleTitle;

    private String authorName;

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

}
