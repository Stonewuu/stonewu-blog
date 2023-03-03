package com.stonewu.blog.admin.config.auth.handler;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stonewu.blog.admin.config.TokenManager;
import com.stonewu.blog.core.entity.SysUser;
import com.stonewu.blog.core.entity.auth.AdminUserLoginToken;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.SysUserService;
import com.stonewu.blog.core.utils.RequestWebUtils;
import com.stonewu.blog.core.utils.WebOutUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private SysUserService userService;

    @Autowired
    private TokenManager tokenManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String userName = (String) authentication.getPrincipal();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        SysUser user = userService.getOne(wrapper);

        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        // 保存本次登录ip
        String host = RequestWebUtils.getIp(request);
        // 生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // 创建token实体
        AdminUserLoginToken loginToken = new AdminUserLoginToken(userName, token, user, new Date(), host);
        // 存入redis
        tokenManager.createNewToken(loginToken);
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        // 将页面返回token
        ObjectResult result = new ObjectResult(ApiResultType.SUCCESS, data);
        WebOutUtils.out(request, response, result);

    }

}
