package com.stonewu.blog.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stonewu.blog.core.entity.SysRole;
import com.stonewu.blog.core.entity.SysUser;
import com.stonewu.blog.core.entity.SysUserRole;
import com.stonewu.blog.core.mapper.SysRoleMapper;
import com.stonewu.blog.core.mapper.SysUserRoleMapper;
import com.stonewu.blog.core.service.BaseServiceImpl;
import com.stonewu.blog.core.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stonewu
 * @since 2018-07-23
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Override
    public List<SysRole> selectRoleByUser(SysUser sysUser) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", sysUser.getId());
        List<SysUserRole> list = userRoleMapper.selectList(wrapper);
        QueryWrapper<SysRole> roleWrapper = new QueryWrapper<>();
        List<Integer> roleIds = list.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        roleWrapper.in("id", roleIds);
        List<SysRole> roleList = this.list(roleWrapper);
        return roleList;
    }

}
