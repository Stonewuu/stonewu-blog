package com.stonewu.blog.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WebOutUtils {
    private static final Logger logger = LoggerFactory.getLogger(WebOutUtils.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 是否是Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

    /**
     * response 输出JSON
     *
     * @param resultMap
     * @throws IOException
     */
    public static void out(ServletRequest request, ServletResponse response, Object resultMap) {
        HttpServletResponse httpResponse = ((HttpServletResponse) response);
        PrintWriter out = null;
        try {
            httpResponse.addHeader("Access-Control-Allow-Origin", "*");
            httpResponse.addHeader("Access-Control-Allow-Headers", "*");
            httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.addHeader("Access-Control-Allow-Headers", "x-requested-with");
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
            httpResponse.addHeader("Access-Control-Max-Age", "1800");
            httpResponse.addHeader("Allow", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json;charset=UTF-8");// 这句话是解决乱码的
            httpResponse.setStatus(httpResponse.getStatus());
            out = httpResponse.getWriter();
            out.println(mapper.writeValueAsString(resultMap));
        } catch (Exception e) {
            logger.error("输出JSON报错。", e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    public static void enptyOut(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = ((HttpServletResponse) response);
        PrintWriter out = null;
        try {
            httpResponse.addHeader("Access-Control-Allow-Origin", "*");
            httpResponse.addHeader("Access-Control-Allow-Headers", "*");
            httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.addHeader("Access-Control-Allow-Headers", "x-requested-with");
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
            httpResponse.addHeader("Access-Control-Max-Age", "1800");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json;charset=UTF-8");// 这句话是解决乱码的
            httpResponse.setStatus(httpResponse.getStatus());
        } catch (Exception e) {
            logger.error("输出JSON报错。", e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }
}
