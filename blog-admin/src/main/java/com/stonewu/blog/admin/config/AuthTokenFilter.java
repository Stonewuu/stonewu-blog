package com.stonewu.blog.admin.config;

import com.stonewu.blog.admin.config.auth.token.AdminTokenAuthenticationToken;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.MapStringResult;
import com.stonewu.blog.core.utils.RequestWebUtils;
import com.stonewu.blog.core.utils.WebOutUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends AbstractAuthenticationProcessingFilter {

    protected AuthTokenFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String authToken = request.getHeader("X-Token");
        if (StringUtils.isEmpty(authToken)) {
            authToken = request.getParameter("X-Token");
        }
        if (authToken == null) {
            throw new AuthenticationCredentialsNotFoundException("未提供有效的TOKEN");
        }
        String ip = RequestWebUtils.getIp(request);
        AdminTokenAuthenticationToken authentication = new AdminTokenAuthenticationToken(authToken, ip, false);
        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(200);
            WebOutUtils.enptyOut(request, response);
            return;
        }
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String message;
        if (failed.getCause() != null) {
            message = failed.getCause().getMessage();
        } else {
            message = failed.getMessage();
        }

        WebOutUtils.out(request, response, new MapStringResult(ApiResultType.LOGIN_FAIL, message));
    }

}
