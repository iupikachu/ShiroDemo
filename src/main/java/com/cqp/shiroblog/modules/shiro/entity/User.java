package com.cqp.shiroblog.modules.shiro.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenqipeng
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;

    private String password;

    private String username;

    // 后面新加的salt
    private String salt;

    // 一个用户可能有多个角色
    // 定义角色集合
    private List<Role> roles;

}
