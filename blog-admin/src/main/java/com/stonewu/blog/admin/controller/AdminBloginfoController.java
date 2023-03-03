package com.stonewu.blog.admin.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stonewu.blog.admin.config.TokenManager;
import com.stonewu.blog.admin.config.auth.token.AdminTokenAuthenticationToken;
import com.stonewu.blog.core.entity.Bloginfo;
import com.stonewu.blog.core.entity.Blogsetting;
import com.stonewu.blog.core.entity.SysUser;
import com.stonewu.blog.core.entity.auth.AdminUserLoginToken;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.BloginfoService;
import com.stonewu.blog.core.service.BlogsettingService;
import com.stonewu.blog.core.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
@Controller
@RequestMapping("/admin/bloginfo")
public class AdminBloginfoController extends AbstractController {
    @Resource
    private BloginfoService bloginfoService;
    @Resource
    private BlogsettingService blogsettingService;
    @Resource
    private SysUserService sysUserService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Resource
    private TokenManager tokenManager;

    @RequestMapping("/showBlogInfo")
    @ResponseBody
    public ObjectResult showBlogInfo() {
        Bloginfo info = bloginfoService.getOne(Wrappers.<Bloginfo>lambdaQuery().eq(Bloginfo::getActive, 1));
        Blogsetting setting = blogsettingService.getOne(Wrappers.<Blogsetting>lambdaQuery().eq(Blogsetting::getActive, 1));

        Map<String, Object> result = new HashMap<>();
        result.put("info", info);
        result.put("setting", setting);
        return new ObjectResult(ApiResultType.SUCCESS, result);
    }

    @RequestMapping("/saveInfo")
    @ResponseBody
    public ObjectResult saveInfo(Bloginfo info) {

        if (info.getId() == null) {
            info.setActive(true);
            bloginfoService.save(info);
        } else {
            bloginfoService.updateById(info);
        }

        return new ObjectResult(ApiResultType.SUCCESS);
    }

    @RequestMapping("/saveSetting")
    @ResponseBody
    public ObjectResult saveSetting(Blogsetting blogsetting) {

        if (blogsetting.getId() == null) {
            blogsetting.setActive(true);
            blogsetting.setSettingName("默认设置");
            blogsettingService.save(blogsetting);
        } else {
            blogsettingService.updateById(blogsetting);
        }
        return new ObjectResult(ApiResultType.SUCCESS);
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public ObjectResult changePassword(String oldPassword, String newPassword) {
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            return new ObjectResult(ApiResultType.PARAM_ERROR, "密码不能为空");
        }
        AdminTokenAuthenticationToken authentication = (AdminTokenAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String authToken = authentication.getToken();
        AdminUserLoginToken token = tokenManager.getTokenForSeries(authToken);

        SysUser user = token.getUser();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return new ObjectResult(ApiResultType.LOGIN_FAIL, "旧密码不正确！");
        }
        newPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);
        sysUserService.updateById(user);
        token.setUser(user);
        tokenManager.createNewToken(token);

        return new ObjectResult(ApiResultType.SUCCESS);
    }
}

