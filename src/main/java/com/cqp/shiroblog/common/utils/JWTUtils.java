package com.cqp.shiroblog.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName JWTUtils.java
 * @Description java web token 工具
 * @createTime 2021年03月06日 14:44:00
 */
public class JWTUtils {

    // 秘钥
    private static final String SECRET_KEY = "!@#$%^&*BJDFTY!@#^!&^#";

    /*
    生成 token header.payload.signature
     */
    public static String getToken(Map<String,String> map ){

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,7); // 默认七天过期

        // 创建 jwt builder
        JWTCreator.Builder builder = JWT.create();

        // PayLoad
        map.forEach((key,value) ->{
            builder.withClaim(key,value);
        });

        // 指定令牌过期时间
        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SECRET_KEY));// sign 签名

        return token;
    }
    /**
     * 验证 token 合法性
     * 验签过程中有错误的话会抛出异常
     */

    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
    }

    /**
     * 获取token信息
     *  decodeJWT.getClaims().get("userid").asInt()
     *  decodeJWT.getClaims().get("username").asString() 去拿用户信息
     */
    public static DecodedJWT getTokenInfo(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        return decodedJWT;
    }

    /*
  获取请求头中的 token
  */
    public static String getRequestToken(HttpServletRequest httpServletRequest){
        // 从header中获取token
        String token = httpServletRequest.getHeader("Authorization");
        // 如果header没有，就从参数中拿
        if(StringUtils.isBlank(token)){
            token = httpServletRequest.getParameter("Authorization");
        }
        return token;
    }
}
