package com.stonewu.blog.admin.config;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stonewu.blog.core.entity.SysUser;
import com.stonewu.blog.core.entity.auth.AdminUserDetail;
import com.stonewu.blog.core.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Override
    public AdminUserDetail loadUserByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        SysUser user = userService.getOne(wrapper);
        // SysUser user = userService.getUserByUserName(username);
        if (user == null) {
            return null;
        }
        return coverUserToDetail(user);
    }

    private AdminUserDetail coverUserToDetail(SysUser user) {
        AdminUserDetail detail = new AdminUserDetail();
        detail.setUsername(user.getUserName());
        detail.setPassword(user.getPassword());
        return detail;
    }

}
