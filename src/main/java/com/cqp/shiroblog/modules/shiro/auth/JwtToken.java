package com.cqp.shiroblog.modules.shiro.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName JwtToken.java
 * @Description TODO
 * @createTime 2021年03月07日 12:51:00
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String jwt){
        this.token = jwt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
