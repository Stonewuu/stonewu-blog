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
 * @since 2019-09-18
 */
public class Blogsetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设置名称
     */
    private String settingName;

    /**
     * 是否启用
     */
    private Boolean active;

    /**
     * 博客是否开放
     */
    private Boolean open;

    /**
     * 开启recaptcha
     */
    private Boolean openRecaptcha;

    private Boolean openXssProtect;

    /**
     * 博客地址
     */
    private String blogDomain;

    /**
     * CDN地址
     */
    private String cdnDomain;

    /**
     * 上传文件地址
     */
    private String uploadPath;

    /**
     * 回复审核
     */
    private Boolean checkReply;

    /**
     * 静态资源版本
     */
    private String resourceVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getOpenRecaptcha() {
        return openRecaptcha;
    }

    public void setOpenRecaptcha(Boolean openRecaptcha) {
        this.openRecaptcha = openRecaptcha;
    }

    public Boolean getOpenXssProtect() {
        return openXssProtect;
    }

    public void setOpenXssProtect(Boolean openXssProtect) {
        this.openXssProtect = openXssProtect;
    }

    public String getBlogDomain() {
        return blogDomain;
    }

    public void setBlogDomain(String blogDomain) {
        this.blogDomain = blogDomain;
    }

    public String getCdnDomain() {
        return cdnDomain;
    }

    public void setCdnDomain(String cdnDomain) {
        this.cdnDomain = cdnDomain;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public Boolean getCheckReply() {
        return checkReply;
    }

    public void setCheckReply(Boolean checkReply) {
        this.checkReply = checkReply;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    @Override
    public String toString() {
        return "Blogsetting{" +
                "id=" + id +
                ", settingName='" + settingName + '\'' +
                ", active=" + active +
                ", open=" + open +
                ", openRecaptcha=" + openRecaptcha +
                ", openXssProtect=" + openXssProtect +
                ", blogDomain='" + blogDomain + '\'' +
                ", cdnDomain='" + cdnDomain + '\'' +
                ", uploadPath='" + uploadPath + '\'' +
                ", checkReply=" + checkReply +
                ", resourceVersion='" + resourceVersion + '\'' +
                '}';
    }
}
