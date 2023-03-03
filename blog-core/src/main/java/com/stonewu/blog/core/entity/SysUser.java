package com.stonewu.blog.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 后台用户
 * </p>
 *
 * @author stonewu
 * @since 2019-08-21
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    private String name;

    private String userName;

    private String password;

    private String salt;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer linkMenberId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLinkMenberId() {
        return linkMenberId;
    }

    public void setLinkMenberId(Integer linkMenberId) {
        this.linkMenberId = linkMenberId;
    }

    @Override
    public String toString() {
        return "SysUser{" +
        "id=" + id +
        ", name=" + name +
        ", userName=" + userName +
        ", password=" + password +
        ", salt=" + salt +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", linkMenberId=" + linkMenberId +
        "}";
    }
}
