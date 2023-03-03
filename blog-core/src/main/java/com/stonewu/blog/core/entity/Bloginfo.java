package com.stonewu.blog.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stonewu
 * @since 2019-09-04
 */
public class Bloginfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 配置名称
     */
    private String infoName;

    /**
     * 是否启用
     */
    private Boolean active;

    /**
     * 博客名称
     */
    private String blogName;

    /**
     * 作者
     */
    private String blogOwner;

    /**
     * 招呼语
     */
    private String helloMsg;

    /**
     * 使用背景图
     */
    private Boolean useBg;

    /**
     * 中间背景图
     */
    private String middleBg;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getBlogOwner() {
        return blogOwner;
    }

    public void setBlogOwner(String blogOwner) {
        this.blogOwner = blogOwner;
    }

    public String getHelloMsg() {
        return helloMsg;
    }

    public void setHelloMsg(String helloMsg) {
        this.helloMsg = helloMsg;
    }

    public Boolean getUseBg() {
        return useBg;
    }

    public void setUseBg(Boolean useBg) {
        this.useBg = useBg;
    }

    public String getMiddleBg() {
        return middleBg;
    }

    public void setMiddleBg(String middleBg) {
        this.middleBg = middleBg;
    }

    @Override
    public String toString() {
        return "Bloginfo{" +
        "id=" + id +
        ", infoName=" + infoName +
        ", active=" + active +
        ", blogName=" + blogName +
        ", blogOwner=" + blogOwner +
        ", helloMsg=" + helloMsg +
        ", useBg=" + useBg +
        ", middleBg=" + middleBg +
        "}";
    }
}
