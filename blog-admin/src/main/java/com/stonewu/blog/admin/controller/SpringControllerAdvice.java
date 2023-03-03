package com.stonewu.blog.admin.controller;

import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.enums.ManagerResultType;
import com.stonewu.blog.core.entity.result.MapStringResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@CrossOrigin
public class SpringControllerAdvice {
    private Logger logger = LogManager.getLogger(SpringControllerAdvice.class);

    /**
     * 404
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public MapStringResult noHandlerFound(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return new MapStringResult(ApiResultType.NO_FOUND);
    }

    /**
     * 请求方式不支持
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public MapStringResult methodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, HttpServletResponse response) {
        return new MapStringResult(ApiResultType.REQUEST_ERROR, "不支持该种请求方式：" + ex.getMethod());
    }

    /**
     * 权限不足
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    public MapStringResult invaildCredentials(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return new MapStringResult(ApiResultType.PERMISSION_DENIED);
    }

    /**
     * 登录失败
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    public MapStringResult noPremission(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return new MapStringResult(ApiResultType.NO_LOGIN);
    }

    /**
     * 其他异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public MapStringResult handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error(ex);
        return new MapStringResult(ManagerResultType.UNKNOW_ERROR.getCode(), ex.getMessage(), null);
    }
}