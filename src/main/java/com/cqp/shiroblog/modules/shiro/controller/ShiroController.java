package com.cqp.shiroblog.modules.shiro.controller;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName ShiroController.java
 * @Description TODO
 * @createTime 2021年03月05日 09:25:00
 */

import com.cqp.shiroblog.common.utils.JWTUtils;
import com.cqp.shiroblog.modules.shiro.dto.LoginDTO;
import com.cqp.shiroblog.modules.shiro.entity.Permission;
import com.cqp.shiroblog.modules.shiro.entity.Role;
import com.cqp.shiroblog.modules.shiro.entity.User;
import com.cqp.shiroblog.modules.shiro.mapper.UserMapper;
import com.cqp.shiroblog.modules.shiro.service.ShiroService;
import com.cqp.shiroblog.modules.shiro.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "shiro权限管理")
@RestController
public class ShiroController {
    @Autowired
    ShiroService shiroService;
    @Autowired
    UserService userService;

    /**
     *  登录
     */
    @ApiOperation(value = "登陆",notes = "参数:用户名 密码")
    @PostMapping("/sys/login")
    public Map<String, Object> login(@RequestBody @Validated LoginDTO loginDTO, BindingResult bindingResult , HttpServletResponse response){
        Map<String,Object> result = new HashMap<>();
        // 用户名和密码不为空
        if(bindingResult.hasErrors()){
          result.put("status",400);
          result.put("msg",bindingResult.getFieldError().getDefaultMessage());
          return result;
        }
           User user = shiroService.findByUsername(loginDTO.getUsername());
         if(user == null){
             result.put("status",401);
             result.put("msg","用户不存在");
             return result;
         }
        // 进行密码校验
        String salt = user.getSalt();
        System.out.println("salt: "+salt);
        Md5Hash md5Hash = new Md5Hash(loginDTO.getPassword(),salt,1024);
        System.out.println("md5Hash: "+md5Hash.toHex());
        System.out.println("password: "+user.getPassword());
        if(!user.getPassword().equals(md5Hash.toHex())){
            result.put("status","402");
            result.put("msg","密码错误");
            return result;
        }
        // 密码正确之后 登录成功 生成token给用户
        HashMap<String, String> payLoad = new HashMap<>();
        payLoad.put("user_id",user.getUserId().toString());
        payLoad.put("username",user.getUsername());
        String token = JWTUtils.getToken(payLoad);
        response.setHeader("Authorization",token);
        response.setHeader("Access-Control-Expose-Headers","Authorization");
        result.put("status",200);
        result.put("msg","登录成功");
        return result;
    }

    /**
     * 退出
     */
    @ApiOperation(value = "登出")
    @RequiresAuthentication
    @PostMapping("/sys/logout")
    public Map<String, Object> logout (){
      SecurityUtils.getSubject().logout();
      Map<String,Object> result = new HashMap<>();
      result.put("status",200);
      result.put("msg","退出成功");
      return result;
    }
    /**
     * 用户注册
     */
    @ApiOperation(value = "注册",notes = "参数:用户名 密码")
    @RequestMapping("/sys/register")
    public Object register(User user){
        Map<String, Object> result = new HashMap<>();
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        try {
            shiroService.register(user);
            result.put("status","200");
            result.put("msg","注册成功");
            String username = user.getUsername();
            result.put("用户",username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    测试
     */
     @ApiOperation(value ="测试" )
     @RequiresAuthentication
     @RequiresPermissions({"delete"}) //没有的话 AuthorizationException
     @RequiresRoles("svip") //没有的话 AuthorizationException
     @GetMapping("/sys/test")
    public Object test(){
         HashMap<String, Object> result = new HashMap<>();
         result.put("status",200);
         result.put("msg","测试成功");
         return result;
     }
}
