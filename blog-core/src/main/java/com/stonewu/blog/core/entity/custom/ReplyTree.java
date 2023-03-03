package com.stonewu.blog.core.entity.custom;

import com.stonewu.blog.core.entity.Reply;

import java.util.ArrayList;
import java.util.List;

public class ReplyTree extends Reply {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String articleTitle;

    private String authorName;

    private List<ReplyTree> children = new ArrayList<>();

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

    public List<ReplyTree> getChildren() {
        return children;
    }

    public void setChildren(List<ReplyTree> children) {
        this.children = children;
    }

}
