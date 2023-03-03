package com.stonewu.blog.admin.config;

import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.MapStringResult;
import com.stonewu.blog.core.utils.WebOutUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        MapStringResult result = new MapStringResult(ApiResultType.NO_LOGIN, "您还未登录，请登陆后再试！");
        WebOutUtils.out(request, response, result);
    }

}
