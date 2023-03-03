package com.stonewu.blog.core.entity.custom;

import com.stonewu.blog.core.entity.Article;

public class ArticleWithMoreInfo extends Article {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String className;

    private String classIdentName;

    private String authorName;

    private Integer year;

    private int replyNum;

    private long viewNum;

    public String getClassIdentName() {
        return classIdentName;
    }

    public void setClassIdentName(String classIdentName) {
        this.classIdentName = classIdentName;
    }

    public long getViewNum() {
        return viewNum;
    }

    public void setViewNum(long viewNum) {
        this.viewNum = viewNum;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
