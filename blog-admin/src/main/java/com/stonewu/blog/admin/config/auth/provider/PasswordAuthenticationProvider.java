package com.stonewu.blog.admin.config.auth.provider;

import com.stonewu.blog.admin.config.AdminUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AdminUserDetailsService userDetailsService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = (String) authentication.getPrincipal(); // 这个获取表单输入中返回的用户名;
        String password = (String) authentication.getCredentials(); // 这个是表单中输入的密码；

        UserDetails userInfo = userDetailsService.loadUserByUsername(userName);

        if (userInfo == null || !passwordEncoder.matches(password, userInfo.getPassword())) {
            throw new BadCredentialsException("用户名密码不正确，请重新登陆！");
        }
        return new UsernamePasswordAuthenticationToken(userName, userInfo, userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
