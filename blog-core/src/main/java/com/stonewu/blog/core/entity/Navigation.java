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
public class Navigation implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 唯一标识
     */
    private String identName;

    /**
     * 导航链接
     */
    private String navLink;

    /**
     * 导航名称
     */
    private String navName;

    /**
     * 排序
     */
    private Integer orderBy;

    /**
     * 是否外链
     */
    private Boolean outLink;

    private LocalDateTime createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getIdentName() {
        return identName;
    }

    public void setIdentName(String identName) {
        this.identName = identName;
    }

    public String getNavLink() {
        return navLink;
    }

    public void setNavLink(String navLink) {
        this.navLink = navLink;
    }

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getOutLink() {
        return outLink;
    }

    public void setOutLink(Boolean outLink) {
        this.outLink = outLink;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Navigation{" +
        "id=" + id +
        ", enable=" + enable +
        ", identName=" + identName +
        ", navLink=" + navLink +
        ", navName=" + navName +
        ", orderBy=" + orderBy +
        ", outLink=" + outLink +
        ", createTime=" + createTime +
        "}";
    }
}
