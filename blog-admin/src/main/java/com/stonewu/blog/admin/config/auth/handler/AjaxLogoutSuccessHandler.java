package com.stonewu.blog.admin.config.auth.handler;

import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.MapStringResult;
import com.stonewu.blog.core.utils.WebOutUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MapStringResult result = new MapStringResult(ApiResultType.SUCCESS, "注销成功！您已成功退出！");
        WebOutUtils.out(request, response, result);
    }

}
