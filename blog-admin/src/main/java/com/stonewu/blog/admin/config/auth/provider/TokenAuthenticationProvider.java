package com.stonewu.blog.admin.config.auth.provider;

import com.stonewu.blog.admin.config.TokenManager;
import com.stonewu.blog.admin.config.auth.token.AdminTokenAuthenticationToken;
import com.stonewu.blog.core.entity.auth.AdminUserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenManager tokenManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authToken = (String) authentication.getPrincipal(); // 这个获取表单输入中返回的用户名;
        String host = (String) authentication.getCredentials();
        if (authToken == null) {
            throw new AuthenticationCredentialsNotFoundException("未提供有效的TOKEN");
        }
        AdminUserLoginToken token = tokenManager.getTokenForSeries(authToken);
        if (token == null) {
            throw new AuthenticationCredentialsNotFoundException("未提供有效的TOKEN");
        }
        if (token.getHost().equals(host)) {
            return new AdminTokenAuthenticationToken(authToken, token.getHost(), true);
        } else {
            throw new AuthenticationCredentialsNotFoundException("网络环境发生变更，请重新登录！");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AdminTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
