package com.stonewu.blog.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stonewu.blog.core.entity.SysRole;
import com.stonewu.blog.core.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 后台用户 服务类
 * </p>
 *
 * @author stonewu
 * @since 2018-07-26
 */
public interface SysUserService extends IService<SysUser> {

    SysUser findByUserName(String userName);

    List<SysRole> selectRoleByUser(Integer id);

}
