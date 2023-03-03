package com.stonewu.blog.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author stonewu
 * @since 2019-08-21
 */
public class Reply implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String content;

    private Integer replyType;

    private Integer articleId;

    private Integer authorId;

    private Integer parentReplyId;

    /**
     * 评论审核，0：未审核，1：审核通过，2：审核未通过
     */
    private Integer checkReply;

    private Integer topReplyId;

    private Boolean sendEmail;

    private LocalDateTime createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getParentReplyId() {
        return parentReplyId;
    }

    public void setParentReplyId(Integer parentReplyId) {
        this.parentReplyId = parentReplyId;
    }

    public Integer getCheckReply() {
        return checkReply;
    }

    public void setCheckReply(Integer checkReply) {
        this.checkReply = checkReply;
    }

    public Integer getTopReplyId() {
        return topReplyId;
    }

    public void setTopReplyId(Integer topReplyId) {
        this.topReplyId = topReplyId;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Reply{" +
        "id=" + id +
        ", content=" + content +
        ", replyType=" + replyType +
        ", articleId=" + articleId +
        ", authorId=" + authorId +
        ", parentReplyId=" + parentReplyId +
        ", checkReply=" + checkReply +
        ", topReplyId=" + topReplyId +
        ", sendEmail=" + sendEmail +
        ", createTime=" + createTime +
        "}";
    }
}
