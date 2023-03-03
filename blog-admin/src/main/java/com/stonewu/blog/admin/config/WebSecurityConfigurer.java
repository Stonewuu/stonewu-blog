package com.stonewu.blog.admin.config;

import com.stonewu.blog.admin.config.auth.handler.AjaxAccessDeniedHandler;
import com.stonewu.blog.admin.config.auth.handler.AjaxAuthenticationFailureHandler;
import com.stonewu.blog.admin.config.auth.handler.AjaxAuthenticationSuccessHandler;
import com.stonewu.blog.admin.config.auth.handler.AjaxLogoutSuccessHandler;
import com.stonewu.blog.admin.config.auth.provider.PasswordAuthenticationProvider;
import com.stonewu.blog.admin.config.auth.provider.TokenAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private AjaxAuthenticationEntryPoint authenticationEntryPoint; // 未登陆时返回 JSON 格式的数据给前端（否则为 html）

    @Autowired
    private AjaxAuthenticationSuccessHandler authenticationSuccessHandler; // 登录成功返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    private AjaxAuthenticationFailureHandler authenticationFailureHandler; // 登录失败返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    private AjaxLogoutSuccessHandler logoutSuccessHandler; // 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）

    @Autowired
    private AjaxAccessDeniedHandler accessDeniedHandler; // 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）

    @Autowired
    private PasswordAuthenticationProvider passwordProvider; // 自定义安全认证

    @Autowired
    private TokenAuthenticationProvider tokenProvider; // 自定义安全认证

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String LOGIN_PAGE = "/auth/no_login";
    private static final String LOGIN_HANDLE = "/auth/login";
    private static final String LOGOUT_HANDLE = "/auth/logout";
    private static final String DEV_HANDLE = "/dev/**";
    private static final String DATA_INTERFACE = "/upload/**";

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        // 设置两个不同的认证器，一个认证账号密码，一个认证token
        ProviderManager authenticationManager = new ProviderManager(Arrays.asList(passwordProvider, tokenProvider));
        // 设置为不擦除密码
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        return authenticationManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 加入自定义的安全认证
        auth.authenticationProvider(passwordProvider);

    }

    @Bean
    public AuthTokenFilter authenticationTokenFilterBean() throws Exception {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(Arrays.asList(LOGIN_PAGE, LOGIN_HANDLE, LOGOUT_HANDLE, DEV_HANDLE, DATA_INTERFACE), "/**");// 排除登录和退出路径
        AuthTokenFilter filter = new AuthTokenFilter(matcher);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// 关闭csrf校验
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 启用无状态会话
                .and().httpBasic().authenticationEntryPoint(authenticationEntryPoint) //
                .and().authorizeRequests().antMatchers("/auth/**", "/dev/**", DATA_INTERFACE).permitAll()// 所有跟登录有关的允许放行
                .antMatchers(HttpMethod.OPTIONS).permitAll()// 运行option请求
                .anyRequest().authenticated()// 其他 url 需要身份认证
                .and().formLogin().usernameParameter("userName") // 开启登录
                .loginPage(LOGIN_PAGE).loginProcessingUrl(LOGIN_HANDLE).successHandler(authenticationSuccessHandler) // 登录成功
                .failureHandler(authenticationFailureHandler) // 登录失败
                .permitAll().and().logout().logoutUrl(LOGOUT_HANDLE).logoutSuccessHandler(logoutSuccessHandler).permitAll().and()
                .addFilterAfter(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        ;
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler); // 无权访问 JSON 格式的数据
    }
}
