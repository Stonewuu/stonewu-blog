package com.stonewu.blog.web.filter;

import com.jayway.jsonpath.JsonPath;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.MenberService;
import com.stonewu.blog.core.utils.HttpConnectionHelper;
import com.stonewu.blog.core.utils.ValidateUtils;
import com.stonewu.blog.core.utils.WebOutUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SignInFilter", urlPatterns = "/user/doReply")
public class SignInFilter implements Filter {
    @Resource
    private MenberService menberService;
    @Resource
    private RedisTemplate<String, ObjectResult> redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 在此处拦截验证码
        String captcha = request.getParameter("captcha");
        String url = "https://www.recaptcha.net/recaptcha/api/siteverify?secret=6Lf8grAUAAAAANl0Ta2YxLqosTae9LHKhiMepZPg&response=" + captcha;
        String json = HttpConnectionHelper.sendRequest(url, "GET");
        if (StringUtils.isBlank(json)) {
            WebOutUtils.out(request, response, new ObjectResult(ApiResultType.UNKNOW_ERROR, "请求验证码服务器失败！"));
            return;
        }
        Boolean success = JsonPath.read(json, "$.success");
        if (Boolean.FALSE.equals(success)) {
            WebOutUtils.out(request, response, new ObjectResult(ApiResultType.PERMISSION_DENIED, "本次请求校验未通过，请勿使用爬虫程序提交数据！"));
            return;
        }
        String action = JsonPath.read(json, "$.action");
        if (!"social".equals(action)) {
            WebOutUtils.out(request, response, new ObjectResult(ApiResultType.PERMISSION_DENIED, "操作不一致！"));
            return;
        }

        String nickName = request.getParameter("nickName");
        String email = request.getParameter("email");
        String site = request.getParameter("site");
        Menber menber = menberService.getMenberByNameEmail(nickName, email);
        if (menber == null || menber.getId() == null) {
            if (StringUtils.isBlank(nickName) || StringUtils.isBlank(email)) {
                WebOutUtils.out(request, response, new ObjectResult(ApiResultType.PARAM_ERROR, "请输入昵称和邮箱！"));
                return;
            }
            menber = new Menber();
            if (nickName.length() > 20) {
                nickName = nickName.substring(0, 20);
            }
            if (email.length() > 50) {
                email = email.substring(0, 50);
            }
            boolean b = ValidateUtils.validateEmail(email);
            if(!b){
                WebOutUtils.out(request, response, new ObjectResult(ApiResultType.PARAM_ERROR, "请输入正确的邮箱地址！"));
                return;
            }
            menber.setNickName(nickName);
            menber.setEmail(email);
            menber.setWebsite(site);
            menber.setActive(1);
            menber.setUserType(1);
            menberService.save(menber);
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("menberInfo", menber);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
