package com.cqp.shiroblog.modules.shiro.auth;

import com.cqp.shiroblog.common.utils.HttpContextUtil;
import com.cqp.shiroblog.common.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName AuthFilter.java
 * @Description 过滤器
 * @createTime 2021年03月05日 13:20:00
 */
@Component
public class AuthFilter extends AuthenticatingFilter {
    // 定义jackson对象 进行对象 java 和 json 的转换
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     *  把请求中的token封装为自定义的token
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if(org.springframework.util.StringUtils.isEmpty(jwt)){
            return null;
        }
        return new JwtToken(jwt);

    }

    /**
     * 步骤1. 将所有请求拒绝访问除了（Option请求）
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println("---进入 isAccessAllowed---");
        if(((HttpServletRequest)request).getMethod().equals(RequestMethod.OPTIONS.name())){
             return true; // 放行OPTIONS请求
        }
        return false;
    }

    /**
     *  步骤2.拒绝访问的请求，会调用 onAccessDenied方法,onAccessDenied方法先获取 token,再调用executeLogin方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 先获取请求的token,token 不存在的话就返回
        System.out.println("---进入onAccessDenied---");
        String jwt = JWTUtils.getRequestToken((HttpServletRequest) request);
//        System.out.println("onAccessDenied token: "+token.toString());
        if(StringUtils.isBlank(jwt)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
            httpResponse.setCharacterEncoding("UTF-8");
            Map<String,Object> result = new HashMap<>();
            result.put("status",403);
            result.put("msg","请先登陆");
            String json = MAPPER.writeValueAsString(result);
            httpResponse.getWriter().print(json);
            return false;
        }
        // 校验 jwt
        JWTUtils.verify(jwt);
        // 执行登录
        return executeLogin(request,response); // shiro的 AuthenticatingFilter 中会调用 subject.login(token) 和createToken;
    }

    /*
    token失效的时候调用
     */

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        System.out.println("---进入onloginfailure---");
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
        httpResponse.setCharacterEncoding("UTF-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Map<String, Object> result = new HashMap<>();
            result.put("status", 403);
            result.put("msg", "登录凭证已失效，请重新登录");
            String json = MAPPER.writeValueAsString(result);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
        }
        return false;
    }
}
