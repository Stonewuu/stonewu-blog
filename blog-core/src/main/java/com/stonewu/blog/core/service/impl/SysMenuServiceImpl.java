package com.stonewu.blog.core.service.impl;


import com.stonewu.blog.core.entity.SysMenu;
import com.stonewu.blog.core.entity.SysUser;
import com.stonewu.blog.core.mapper.SysMenuMapper;
import com.stonewu.blog.core.service.BaseServiceImpl;
import com.stonewu.blog.core.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stonewu
 * @since 2018-07-23
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> selectMenuByUser(SysUser sysUser) {
        List<SysMenu> list = sysMenuMapper.selectMenuByUser(sysUser);
        return list;
    }

}
