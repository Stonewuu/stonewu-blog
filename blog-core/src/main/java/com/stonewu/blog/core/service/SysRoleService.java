package com.stonewu.blog.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stonewu.blog.core.entity.SysRole;
import com.stonewu.blog.core.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stonewu
 * @since 2018-07-26
 */
public interface SysRoleService extends IService<SysRole> {

    public List<SysRole> selectRoleByUser(SysUser sysUser);

}
