package com.stonewu.blog.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stonewu.blog.core.entity.SysMenu;
import com.stonewu.blog.core.entity.SysUser;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stonewu
 * @since 2018-07-25
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectMenuByUser(SysUser sysUser);

}
