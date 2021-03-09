package com.cqp.shiroblog.modules.shiro.auth;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cqp.shiroblog.common.utils.JWTUtils;
import com.cqp.shiroblog.modules.shiro.entity.Permission;
import com.cqp.shiroblog.modules.shiro.entity.Role;
import com.cqp.shiroblog.modules.shiro.entity.User;
import com.cqp.shiroblog.modules.shiro.mapper.UserMapper;
import com.cqp.shiroblog.modules.shiro.salt.MyByteSource;
import com.cqp.shiroblog.modules.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName CustomerRealm.java
 * @Description 自定义 Realm
 * @createTime 2021年03月06日 12:37:00
 */
@Component
public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("====CustomerRealm.授权====");
        // 1.获取登录用户的信息
        String user_id=(String) principalCollection.getPrimaryPrincipal();
        // 2.添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = userService.findRolesByUserId(Integer.parseInt(user_id));
        for(Role role : user.getRoles() ){
            // 2.1 添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            Integer roleId = role.getRoleId();
            Role role_permission = userService.findPermissionByRoleId(roleId);
            for(Permission permission : role_permission.getPermissions()){
                //2.1.1添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
                System.out.println("permissions: "+permission.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("====CustomerRealm.认证====");
        DecodedJWT tokenInfo = JWTUtils.getTokenInfo((String) token.getPrincipal());
        String accessToken = (String) token.getPrincipal();
        String user_id = tokenInfo.getClaims().get("user_id").asString();
        /*
        String user_id = tokenInfo.getClaims().get("user_id").asString();
        String username = tokenInfo.getClaims().get("username").asString();
        可以根据 user表的 status字段锁定账户
        if(user.getStatus() == -1){
        throw new LockedAccountException("账户已被锁定");
        }
         */
        return new SimpleAuthenticationInfo(user_id,accessToken,this.getName());
    }
}
