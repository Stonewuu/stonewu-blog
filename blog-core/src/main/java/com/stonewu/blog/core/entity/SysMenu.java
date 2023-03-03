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
 * @since 2019-08-21
 */
public class SysMenu implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    /**
     * 权限代码
     */
    private String permissionCode;

    private String uri;

    private Integer sort;

    private Integer parentId;

    /**
     * 菜单类型，0，根菜单，1：子菜单，2：功能
     */
    private Integer type;

    /**
     * 是否显示（如果为功能菜单，建议不显示）
     */
    private Boolean show;

    /**
     * 菜单描述
     */
    private String desc;


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

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
        "id=" + id +
        ", name=" + name +
        ", permissionCode=" + permissionCode +
        ", uri=" + uri +
        ", sort=" + sort +
        ", parentId=" + parentId +
        ", type=" + type +
        ", show=" + show +
        ", desc=" + desc +
        "}";
    }
}
