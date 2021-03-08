package com.cqp.shiroblog.modules.shiro.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName LoginDTO.java
 * @Description 登录传输类
 * @createTime 2021年03月05日 09:39:00
 */
@Data
public class LoginDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
