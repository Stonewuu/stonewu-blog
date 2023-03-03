package com.stonewu.blog.core.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stonewu.blog.core.entity.SysRole;
import com.stonewu.blog.core.entity.SysUser;
import com.stonewu.blog.core.mapper.SysUserMapper;
import com.stonewu.blog.core.service.BaseServiceImpl;
import com.stonewu.blog.core.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台用户 服务实现类
 * </p>
 *
 * @author stonewu
 * @since 2018-07-23
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUserName(String userName) {
        SysUser param = new SysUser();
        param.setUserName(userName);
        SysUser result = sysUserMapper.selectOne(Wrappers.query(param));
        return result;

    }

    @Override
    public List<SysRole> selectRoleByUser(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }
}
