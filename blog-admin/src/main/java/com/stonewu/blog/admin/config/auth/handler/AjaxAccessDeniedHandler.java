package com.stonewu.blog.admin.config.auth.handler;

import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.MapStringResult;
import com.stonewu.blog.core.utils.WebOutUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        MapStringResult result = new MapStringResult(ApiResultType.PERMISSION_DENIED, "您没有访问该资源的权限，请检查后再试！");
        WebOutUtils.out(request, response, result);
    }

}
